package com.example.patient.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.patient.entity.Response;
import com.example.patient.entity.ValidationException;

@RestControllerAdvice
public class ControllerAdvicePatient {

	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<Response> ValidationPatient(final ValidationException validation) {
		Response response = new Response(false, validation.getMessage(), null);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}

}
