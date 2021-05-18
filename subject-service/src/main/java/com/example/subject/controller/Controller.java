package com.example.subject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.subject.entity.Response;
import com.example.subject.entity.Subject;
import com.example.subject.services.Services;

@RestController
@RequestMapping(path = "/subject")
public class Controller {

	@Autowired
	Services services;
	
	@PostMapping(path = "/save")
	public ResponseEntity<Response> save(@RequestBody Subject subject){
		Response response = services.save(subject);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
		
	@PostMapping(path = "/saveMass")
	public ResponseEntity<Response> saveMass(@RequestBody List<Subject> listSubject){
		Response response = services.saveMass(listSubject);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(path = "/findAll")
	public ResponseEntity<Response> findAll(){
		Response response = services.findAll();
		return new ResponseEntity<>(response,HttpStatus.FOUND);
	}
	
	@GetMapping(path = "/findById")
	public ResponseEntity<Response> findById(@RequestHeader(name = "id") Long id){
		Response response = services.findById(id);
		return new ResponseEntity<>(response,HttpStatus.FOUND);
	}
	
	@GetMapping(path = "/findGradeSubject/{gradeSubject}")
	public ResponseEntity<Response> findGradeSubject(@PathVariable Long gradeSubject){
		Response response = services.findGradeSubject(gradeSubject);
		return new ResponseEntity<>(response,HttpStatus.FOUND);
	}
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<Response> update(@RequestBody Subject subject, @PathVariable Long id ){
		Response response = services.update(subject,id);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/deleteById")
	public ResponseEntity<Response> deleteById(@RequestParam(name = "id") Long id ){
		Response response = services.deleteById(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
}
