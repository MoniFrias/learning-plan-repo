package com.example.student.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.student.entity.NotDataFoundException;
import com.example.student.entity.Response;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(value = NotDataFoundException.class)
	public ResponseEntity<Response> notDataFoundException(final NotDataFoundException exception){
		final Response response = new Response(false, exception.getMessage(), null);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}

}
