package com.example.student.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.example.student.entity.NotDataFoundException;
import com.example.student.entity.Response;
import com.example.student.entity.Student;
import com.example.student.entity.StudentSubject;
import com.example.student.entity.Subject;
import com.example.student.repository.RepositoryStudent;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ServicesTest {

	@InjectMocks
	Services services;
	@Mock
	RepositoryStudent repository;
	@Mock
	WebClient webClient;
	@Mock
	RequestHeadersUriSpec requestHeaderUriSpec;
	@Mock
	RequestHeadersSpec requestHeaderSpec;
	@Mock
	ResponseSpec responseSpec;
	@Mock
	RequestBodyUriSpec requestBodyUriSpec;
	@Mock
	RequestBodySpec requestBodySpec;
	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(services, "subjectFindGradeSubject", "");
		ReflectionTestUtils.setField(services, "subjectSave", "");
	}
	
	@Test
	public void testSave() {
		Student student=new Student();
		assertEquals("success", services.save(student).getMessage());
	}
	
	@Test
	public void testSaveMass1() {
		List<Student> studentList = new ArrayList<>();
		assertEquals("success", services.saveMass1(studentList).getMessage());
	}
	
	@Test
	public void testSaveMass2() {
		List<Student> studentList = new ArrayList<>();
		studentList.add(new Student(1L, "name", "lastName", 1L, 1L, new ArrayList<>()));
		Student student=new Student(1L, "name", "lastName", 1L, 1L, new ArrayList<>());
		when(repository.save(Mockito.any())).thenReturn(student);
		assertEquals("success", services.saveMass2(studentList).getMessage());
	}
	
	@Test
	public void testFindAll() {
		List<Student> studentList = new ArrayList<>();
		studentList.add(new Student(1L, "name", "lastName", 1L, 1L, new ArrayList<>()));
		when(repository.findAll()).thenReturn(studentList);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString(),Mockito.anyLong())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
	    List<Subject> subjectList= new ArrayList<>();
	    subjectList.add(new Subject("name", "nameTeacher", 1L));
		Mono<Response> monoResponse= Mono.just(new Response(true, "message", subjectList));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertTrue(services.findAll().isResult());
	}
	
	@Test
	public void testFindAll_Empty() {
		List<Student> studentList = new ArrayList<>();
		when(repository.findAll()).thenReturn(studentList);
		assertFalse(services.findAll().isResult());
	}
	
	@Test
	public void testFindById() {
		Student student = new Student(1L, "name", "lastName", 1L, 1L, new ArrayList<>());
		when(repository.findStudentById(Mockito.anyLong())).thenReturn(student);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString(),Mockito.anyLong())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
	    List<Subject> subjectList= new ArrayList<>();
	    subjectList.add(new Subject("name", "nameTeacher", 1L));
		Mono<Response> monoResponse= Mono.just(new Response(true, "message", subjectList));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertTrue(services.findById(1L).isResult());
	}
	
	@Test
	public void testFindById_StudentNull() {
		when(repository.findStudentById(Mockito.anyLong())).thenReturn(null);
		assertFalse(services.findById(1L).isResult());
	}
	
	@Test
	public void testFindById_Null() {
		assertFalse(services.findById(null).isResult());
	}
	
	@Test
	public void testFindById_Zero() {
		assertFalse(services.findById(0L).isResult());
	}
	
	@Test
	public void testUpdate() {
		Student student = new Student(1L, "name", "lastName", 1L, 1L, new ArrayList<>());
		when(repository.findStudentById(Mockito.anyLong())).thenReturn(student);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString(),Mockito.anyLong())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
	    List<Subject> subjectList= new ArrayList<>();
	    subjectList.add(new Subject("name", "nameTeacher", 1L));
		Mono<Response> monoResponse= Mono.just(new Response(true, "message", subjectList));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertTrue(services.update(student, 1L).isResult());
	}
	
	@Test
	public void testUpdate_StudentNull() {
		Student student = new Student(1L, "name", "lastName", 1L, 1L, new ArrayList<>());
		when(repository.findStudentById(Mockito.anyLong())).thenReturn(null);
		assertFalse(services.update(student, 1L).isResult());
	}

	@Test
	public void testUpdate_Null() {
		Student student = new Student(1L, "name", "lastName", 1L, 1L, new ArrayList<>());
		assertFalse(services.update(student, null).isResult());
	}
	
	@Test
	public void testUpdate_Zero() {
		Student student = new Student(1L, "name", "lastName", 1L, 1L, new ArrayList<>());
		assertFalse(services.update(student, 0L).isResult());
	}
	
	@Test
	public void testDeleteById() {
		Student student = new Student(1L, "name", "lastName", 1L, 1L, new ArrayList<>());
		when(repository.findStudentById(Mockito.anyLong())).thenReturn(student);
		assertTrue(services.deleteById(1L).isResult());
	}
	
	@Test
	public void testDeleteById_StudentNull() {
		when(repository.findStudentById(Mockito.anyLong())).thenReturn(null);
		assertThrows(NotDataFoundException.class,() -> services.deleteById(1L));
	}
	
	@Test
	public void testDeleteById_Null() {
		assertFalse(services.deleteById(null).isResult());
	}
	
	@Test
	public void testDeleteById_Zero() {
		assertFalse(services.deleteById(0L).isResult());
	}
	
	@Test
	public void testSaveStudentSubject() {
		List<Subject> subjectList=new ArrayList<>();
		subjectList.add(new Subject("name", "nameTeacher", 1L));
		StudentSubject studentSubject=new StudentSubject("name", "lastName", 1L, 1L, subjectList);
		Student student = new Student(1L, "name", "lastName", 1L, 1L, new ArrayList<>());
		
		when(repository.save(Mockito.any())).thenReturn(student);
		when(webClient.post()).thenReturn(requestBodyUriSpec);
		when(requestBodyUriSpec.uri(Mockito.anyString())).thenReturn(requestBodySpec);
		when(requestBodySpec.body(Mockito.any())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
		Subject subject= new Subject("name", "nameTeacher", 1L);
		Mono<Response> monoResponse=Mono.just(new Response(true, "message", subject));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertEquals("success", services.saveStudentSubject(studentSubject).getMessage());
	}
	
	@Test
	public void testSaveStudentSubject_Catch() {
		List<Subject> subjectList=new ArrayList<>();
		subjectList.add(new Subject("name", "nameTeacher", 1L));
		StudentSubject studentSubject=new StudentSubject("name", "lastName", 1L, 1L, subjectList);
		Student student = new Student(1L, "name", "lastName", 1L, 1L, new ArrayList<>());
		
		when(repository.save(Mockito.any())).thenReturn(student);
		when(webClient.post()).thenReturn(requestBodyUriSpec);
		when(requestBodyUriSpec.uri(Mockito.anyString())).thenReturn(requestBodySpec);
		when(requestBodySpec.body(Mockito.any())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
	
		Mono<Response> monoResponse=Mono.just(new Response(true, "message", true));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertEquals("success", services.saveStudentSubject(studentSubject).getMessage());
	}
	
}
