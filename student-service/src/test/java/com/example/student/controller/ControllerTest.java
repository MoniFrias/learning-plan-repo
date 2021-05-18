package com.example.student.controller;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.example.student.entity.Student;
import com.example.student.entity.StudentSubject;
import com.example.student.services.Services;

@ExtendWith(MockitoExtension.class)
class ControllerTest {
	
	@InjectMocks
	Controller controller;
	@Mock
	Services service;
	
	@Test
	public void testSave() {
		Student student =  new Student(1L, "name", "lastName", 1L, 1L,new ArrayList<>());
		assertEquals(HttpStatus.OK, controller.save(student).getStatusCode());
	}
	
	@Test
	public void testSaveMass1() {
		List<Student> studentList =  new ArrayList<>();
		assertEquals(HttpStatus.OK, controller.saveMass1(studentList).getStatusCode());
	}
	
	@Test
	public void testSaveMass2() {
		List<Student> studentList =  new ArrayList<>();
		assertEquals(HttpStatus.OK, controller.saveMass2(studentList).getStatusCode());
	}

	@Test 
	public void testFindAll() {
		assertNotNull(controller.findAll());
	}
	
	@Test 
	public void testFindById() {
		assertNotNull(controller.findById(1L));
	}
	
	@Test 
	public void testUpdate() {
		Student student =  new Student(1L, "name", "lastName", 1L, 1L,new ArrayList<>());
		assertNotNull(controller.update(student, 1L));
	}
	
	@Test 
	public void testDeleteById() {
		assertNotNull(controller.deleteById(1L));
	}
	
	@Test
	public void testSaveStudentSubject() {
		StudentSubject studentSubject = new StudentSubject();
		assertEquals(HttpStatus.CREATED, controller.saveStudentSubject(studentSubject).getStatusCode());
	}
}
