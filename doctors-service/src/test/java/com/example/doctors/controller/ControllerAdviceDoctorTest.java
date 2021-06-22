package com.example.doctors.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.example.doctors.entity.ValidationException;

@ExtendWith(MockitoExtension.class)
class ControllerAdviceDoctorTest {

	@InjectMocks
	ControllerAdviceDoctor controllerA;
	
	@Test
	public void validationDoctorTest() {
		ValidationException validation = new ValidationException("");
		assertEquals(HttpStatus.BAD_REQUEST, controllerA.ValidationDoctor(validation).getStatusCode());
	}

}
