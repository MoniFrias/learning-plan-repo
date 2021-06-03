package com.example.subject.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.subject.entity.Response;
import com.example.subject.entity.Subject;
import com.example.subject.repository.RepositorySubject;

@Service
public class Services {
	
	@Autowired
	RepositorySubject repository;
	
	
	private Response errorResponse(String message) {
		return new Response(false, message, null);
	}

	public Response save(Subject subject) {
		Response response = new Response();
		response.setData(repository.save(subject));
		return response;
	}

	public Response saveMass(List<Subject> listSubject) {
		Response response = new Response();
		List<Subject> listSubjectSave = listSubject.stream().map(subject -> {
			Subject subjectSave = repository.save(subject);
			return subjectSave;
		}).filter(Objects::nonNull).collect(Collectors.toList());		
		response.setData(listSubjectSave);
		return response;
	}

	public Response findAll() {
		Response response = new Response();
		List<Subject> listSubject = repository.findAll();
		if (!listSubject.isEmpty()) {
			response.setData(listSubject);
			return response;
		}
		return errorResponse("No registered subject was found");
	}

	public Response findById(Long id) {
		Response response = new Response();
		if (id!=null && id>0) {
			Subject subject = repository.findSubjectById(id);
			if (subject!=null) {
				response.setData(subject);
				return response;
			}
			return errorResponse("No subject was found with that ID");			
		}
		return errorResponse("ID can't be null or zero");
	}
	
	public Response findGradeSubject(Long gradeSubject) {
		Response response = new Response();
		if (gradeSubject!=null && gradeSubject>0) {
			List<Subject> subject = repository.findSubjectByGradeSubject(gradeSubject);
			if (!subject.isEmpty()) {
				response.setData(subject);
				return response;
			}
			return errorResponse("No subject was found with that ID grade");			
		}
		return errorResponse("ID can't be null or zero");
	}

	public Response update(Subject subject, Long id) {
		Response response = new Response();
		if (id!=null && id>0) {
			Subject subjectFound = repository.findSubjectById(id);
			if (subjectFound!=null) {
				subjectFound.setName(subject.getName());
				subjectFound.setNameTeacher(subject.getNameTeacher());
				subjectFound.setGradeSubject(subject.getGradeSubject());
				response.setData(repository.save(subjectFound));
				return response;
			}
			return errorResponse("No subject was found with that ID");
		}
		return errorResponse("ID can't be null or zero");
	}

	public Response deleteById(Long id) {
		Response response = new Response();
		if (id!=null && id>0) {
			Subject subjectFound = repository.findSubjectById(id);
			if (subjectFound!=null) {
				repository.deleteById(id);
				return response;
			}
			return errorResponse("No subject was found with that ID");
		}
		return errorResponse("ID can't be null or zero");
	}

}
