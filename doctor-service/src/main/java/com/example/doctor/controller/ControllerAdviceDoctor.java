package com.example.doctor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.doctor.entity.Response;
import com.example.doctor.entity.ValidationException;

@RestControllerAdvice
public class ControllerAdviceDoctor {

	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<Response> ValidationDoctor(final ValidationException validation) {
		Response response = new Response(false, validation.getMessage(), null);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}

}
