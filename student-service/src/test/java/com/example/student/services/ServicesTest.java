package com.example.student.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.student.entity.Student;
import com.example.student.repository.RepositoryStudent;

@ExtendWith(MockitoExtension.class)
class ServicesTest {

	@InjectMocks
	Services services;
	@Mock
	RepositoryStudent repository;
	
	@Test
	public void testSave () {
		Student student=new Student();
		assertEquals("success", services.save(student).getMessage());
	}
	
	@Test
	public void testSaveMass1 () {
		Student student=new Student();
		assertEquals("success", services.save(student).getMessage());
	}

}
