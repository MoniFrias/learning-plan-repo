package com.example.doctors.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.doctors.entity.Response;
import com.example.doctors.entity.ValidationException;

@RestControllerAdvice
public class ControllerAdviceDoctor {

	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<Response> ValidationDoctor(final ValidationException validation) {
		Response response = new Response(false, validation.getMessage(), null);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}

}
