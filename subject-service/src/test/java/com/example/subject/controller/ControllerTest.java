package com.example.subject.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.example.subject.entity.Subject;
import com.example.subject.services.Services;

import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
class ControllerTest {
	
	@InjectMocks
	Controller controller;
	@Mock
	Services services;

	@Test
	public void testSave() {
		Subject subject= new Subject();
		assertEquals(HttpStatus.OK,controller.save(subject).getStatusCode());
	}

	@Test
	public void testSaveMass() {
		List<Subject> subjectList = new ArrayList<>();
		assertEquals(HttpStatus.OK,controller.saveMass(subjectList).getStatusCode());
	}
	
	@Test
	public void testFindAll() {
		assertEquals(HttpStatus.FOUND,controller.findAll().getStatusCode());
	}
	
	@Test
	public void testFindById() {
		assertEquals(HttpStatus.FOUND,controller.findById(1L).getStatusCode());
	}
	
	@Test
	public void testFindGradeSubject() {
		assertEquals(HttpStatus.FOUND,controller.findGradeSubject(1L).getStatusCode());
	}
	
	@Test
	public void testUpdate() {
		Subject subject= new Subject();
		assertEquals(HttpStatus.CREATED,controller.update(subject,1L).getStatusCode());
	}
	
	@Test
	public void testDeleteById() {
		assertEquals(HttpStatus.OK,controller.deleteById(1L).getStatusCode());
	}
}
