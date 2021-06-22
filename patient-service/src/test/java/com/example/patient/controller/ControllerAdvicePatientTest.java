package com.example.patient.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.example.patient.entity.ValidationException;

@ExtendWith(MockitoExtension.class)
class ControllerAdvicePatientTest {

	@InjectMocks
	ControllerAdvicePatient controllerA;
	
	@Test
	public void ValidationPatientTest() {
		ValidationException validation = new ValidationException("");
		assertEquals(HttpStatus.BAD_REQUEST, controllerA.ValidationPatient(validation).getStatusCode());
	}

}
