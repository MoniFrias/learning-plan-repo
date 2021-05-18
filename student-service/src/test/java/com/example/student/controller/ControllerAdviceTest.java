package com.example.student.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.example.student.entity.NotDataFoundException;

@ExtendWith(MockitoExtension.class)
class ControllerAdviceTest {
	
	@InjectMocks
	ControllerAdvice controllerAdvice;

	@Test
	public void testNotDataFoundException() {
		NotDataFoundException exception = new NotDataFoundException("");
		assertEquals(HttpStatus.NOT_FOUND, controllerAdvice.notDataFoundException(exception));
	}

}
