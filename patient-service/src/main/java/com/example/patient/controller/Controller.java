package com.example.patient.controller;

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

import com.example.patient.entity.Patient;
import com.example.patient.entity.Response;
import com.example.patient.services.Services;

@RestController
@RequestMapping(path = "/patient")
public class Controller {

	@Autowired
	Services services;
	
	@PostMapping(path = "/save")
	public ResponseEntity<Response> save(@RequestBody Patient patient){
		Response response = services.save(patient);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(path = "/saveMass")
	public ResponseEntity<Response> saveMass(@RequestBody List<Patient> listPatient){
		Response response = services.saveMass(listPatient);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(path = "/findAll")
	public ResponseEntity<Response> findAll(){
		Response response = services.findAll();
		return new ResponseEntity<>(response,HttpStatus.FOUND);
	}
	
	@GetMapping(path = "/findById")
	public ResponseEntity<Response> findById(@RequestParam(name = "id") Long id){
		Response response = services.findById(id);
		return new ResponseEntity<>(response,HttpStatus.FOUND);
	}
	
	@GetMapping(path = "/findByIdDoctor")
	public ResponseEntity<Response> findByIdDoctor(@RequestParam(name = "id") Long id){
		Response response = services.findByIdDoctor(id);
		return new ResponseEntity<>(response,HttpStatus.FOUND);
	}
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<Response> update(@RequestBody Patient patient, @PathVariable Long id){
		Response response = services.update(patient,id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/deleteById")
	public ResponseEntity<Response> deleteById(@RequestHeader(name = "id") Long id){
		Response response = services.deleteById(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

}
