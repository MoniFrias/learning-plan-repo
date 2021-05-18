package com.example.student.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.student.entity.NotDataFoundException;
import com.example.student.entity.Response;
import com.example.student.entity.Student;
import com.example.student.entity.StudentSubject;
import com.example.student.entity.Subject;
import com.example.student.repository.RepositoryStudent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Services {

	@Autowired
	RepositoryStudent repository;
	@Autowired
	WebClient webClient;
	@Value("${subjectFindGradeSubject}")
	private String subjectFindGradeSubject;
	@Value("${subjectSave}")
	private String subjectSave;
	
	private Response errorResponse(String message) {
		return new Response(false, message, null);
	}

	public Response save(Student student) {
		Response response = new Response();
		response.setData(repository.save(student));
		return response;
	}

	public Response saveMass1(List<Student> listStudent) {
		Response response = new Response();
		response.setData(repository.saveAll(listStudent));
		return response;
	}
	
	public Response saveMass2(List<Student> listStudent) {
		Response response = new Response();
		List<Student> listStudentSave = listStudent.stream().map(student -> {
			Student studentSave = repository.save(student);
			return studentSave;
		}).filter(Objects::nonNull).collect(Collectors.toList());		
		response.setData(listStudentSave);
		return response;
	}

	@SuppressWarnings("unchecked")
	private List<Subject> findListSubject(Long gradeSubject){
		return (List<Subject>) webClient.get().uri(subjectFindGradeSubject,gradeSubject)
				.retrieve().bodyToMono(Response.class).block().getData();
	}
	
	public Response findAll() {
		Response response = new Response();
		List<Student> listStudent = repository.findAll();
		if (!listStudent.isEmpty()) {
			List<Student> listStudentNew = listStudent.stream().map(student ->{
				student.setListSubject(findListSubject(student.getGrade()));
				return student;
			}).filter(Objects::nonNull).collect(Collectors.toList());			
			response.setData(listStudentNew);
			return response;
		}
		return errorResponse("No registered students found");
	}

	public Response findById(Long id) {
		Response response = new Response();
		if (id!=null && id>0) {
			Student student = repository.findStudentById(id);
			if (student!=null) {
				student.setListSubject(findListSubject(student.getGrade()));
				response.setData(student);
				return response;
			}
			return errorResponse("No student found with that ID");			
		}
		return errorResponse("ID can't be null or zero");
	}

	public Response update(Student student, Long id) {
		Response response = new Response();
		if (id!=null && id>0) {
			Student studentFound = repository.findStudentById(id);
			if (studentFound!=null) {
				studentFound.setName(student.getName());
				studentFound.setLastName(student.getLastName());
				studentFound.setIdStudent(student.getIdStudent());
				studentFound.setGrade(student.getGrade());
				studentFound.setListSubject(findListSubject(student.getGrade()));
				response.setData(repository.save(studentFound));
				return response;
			}
			return errorResponse("No student found with that ID");
		}
		return errorResponse("ID can't be null or zero");
	}

	public Response deleteById(Long id) {
		Response response = new Response();
		if (id!=null && id>0) {
			Student studentFound = repository.findStudentById(id);
			if (studentFound!=null) {
				repository.deleteById(id);
				return response;
			}
			throw new NotDataFoundException("Student not found for ID ".concat(id.toString()));
		}
		return errorResponse("ID can't be null or zero");
	}
	
	private Student saveStudent(StudentSubject studentSubject) {
		Student student = new Student();
		student.setName(studentSubject.getName());
		student.setLastName(studentSubject.getLastName());
		student.setIdStudent(studentSubject.getIdStudent());
		student.setGrade(studentSubject.getGrade());
		return student;
	}
	
	private Subject createSubject(Subject subject,  Long gradeSubject) {
		Subject subjectNew = new Subject();
		subjectNew.setName(subject.getName());
		subjectNew.setNameTeacher(subject.getNameTeacher());
		subjectNew.setGradeSubject(gradeSubject);
		return subjectNew;
	}

	private Subject saveSubject(Subject subject) throws JsonProcessingException{
		Object objectSubject=  webClient.post().uri(subjectSave)
				.body(BodyInserters.fromValue(subject))
				.retrieve().bodyToMono(Response.class)
				.block().getData();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String stringResponse = objectMapper.writeValueAsString(objectSubject);
		Subject responseProduct = objectMapper.readValue(stringResponse, Subject.class);
		return responseProduct;
	}
	
	public Response saveStudentSubject(StudentSubject studentSubject) {
		Response response = new Response();
		Student student = repository.save(saveStudent(studentSubject));
		List<Subject> listSubject = studentSubject.getListSubject();
		List<Subject> listSubjectNew = listSubject.stream().map(subject -> {
			Subject subjectCreate = null;
			try {
				subjectCreate = createSubject(subject, student.getGrade());
				saveSubject(subjectCreate);
			} catch (JsonProcessingException e) {
				log.error("Error saving products {}", e);
				e.printStackTrace();
			}
			return subjectCreate;
		}).filter(Objects::nonNull).collect(Collectors.toList());
		student.setListSubject(listSubjectNew);
		response.setData(student);
		return response;
	}
}
