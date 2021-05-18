package com.example.student.controller;

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

import com.example.student.entity.Response;
import com.example.student.entity.Student;
import com.example.student.entity.StudentSubject;
import com.example.student.services.Services;

@RestController
@RequestMapping(path = "/student")
public class Controller {
	
	@Autowired
	Services services;
	
	@PostMapping(path = "/save")
	public ResponseEntity<Response> save(@RequestBody Student student){
		Response response = services.save(student);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(path = "/saveMass1")
	public ResponseEntity<Response> saveMass1(@RequestBody List<Student> listStudent){
		Response response = services.saveMass1(listStudent);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(path = "/saveMass2")
	public ResponseEntity<Response> saveMass2(@RequestBody List<Student> listStudent){
		Response response = services.saveMass2(listStudent);
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
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<Response> update(@RequestBody Student student, @PathVariable Long id ){
		Response response = services.update(student,id);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/deleteById")
	public ResponseEntity<Response> deleteById(@RequestParam(name = "id") Long id ){
		Response response = services.deleteById(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(path = "/saveStudentSubject")
	public ResponseEntity<Response> saveStudentSubject(@RequestBody StudentSubject studentSubject){
		Response response = services.saveStudentSubject(studentSubject);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

}
