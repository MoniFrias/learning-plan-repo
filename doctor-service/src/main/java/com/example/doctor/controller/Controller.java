package com.example.doctor.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import com.example.doctor.entity.Doctor;
import com.example.doctor.entity.Response;
import com.example.doctor.services.Services;

@RestController
@RequestMapping(path = "/doctor")
public class Controller {

	@Autowired
	Services services;
	
	@PostMapping(path = "/save")
	public ResponseEntity<Response> save(@Valid @RequestBody Doctor doctor,BindingResult validResult){
		Response response = services.save(doctor,validResult);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(path = "/saveMass")
	public ResponseEntity<Response> saveMass(@Valid @RequestBody List<Doctor> listDoctor){
		Response response = services.saveMass(listDoctor);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(path = "/findAll")
	public ResponseEntity<Response> findAll(){
		Response response = services.findAll();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(path = "/findById")
	public ResponseEntity<Response> findById(@RequestParam(name = "id") Long id){
		Response response = services.findById(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(path = "/findByNameAndLastName")
	public ResponseEntity<Response> findByNameAndLastName(@RequestParam(name = "name") String name,@RequestParam(name = "lastname") String lastname ){
		Response response = services.findByNameAndLastName(name,lastname);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(path = "/findByCedula")
	public ResponseEntity<Response> findByCedula(@RequestHeader(name = "cedula") Long cedula){
		Response response = services.findByCedula(cedula);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<Response> update(@RequestBody Doctor doctor, @PathVariable Long id){
		Response response = services.update(doctor,id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@DeleteMapping(path = "/deleteById/{id}")
	public ResponseEntity<Response> deleteById(@PathVariable Long id){
		Response response = services.deleteById(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
}
