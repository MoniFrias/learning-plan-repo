package com.example.practices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.practices.entity.Person;
import com.example.practices.entity.Response;
import com.example.practices.services.Services;

@RestController
@RequestMapping(path = "/person")
public class Controller {

	@Autowired
	Services services;
	
	@PostMapping(path = "/save")
	public ResponseEntity<Response> save(@RequestBody Person person){
		Response response = services.save(person);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/findAll")
	public ResponseEntity<Response> findAll(){
		Response response = services.findAll();
		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}
	
	@GetMapping(path = "/findById/{id}")
	public ResponseEntity<Response> findById(@PathVariable Long id){
		Response response = services.findById(id);
		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<Response> update(@RequestBody Person person, @PathVariable Long id){
		Response response = services.update(person,id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/deleteById")
	public ResponseEntity<Response> deleteById(@RequestParam(name = "id") Long id){
		Response response = services.deleteById(id);
		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}
	
	@GetMapping(path = "/ternaryO")
	public ResponseEntity<Response> ternaryO(){
		Response response = services.ternaryO();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/exceptions1/{position}")
	public ResponseEntity<Response> exceptionsIndexOut(@PathVariable int position){
		Response response = services.exceptionsIndexOut(position);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/exceptions2/{id}")
	public ResponseEntity<Response> exceptionsNullPointer(@PathVariable Long id){
		Response response = services.exceptionsNullPointer(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/exceptionsThrow")
	public ResponseEntity<Response> exceptionsThrow(@RequestParam(name = "age") Long age){
		Response response = services.exceptionsThrow(age);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/list")
	public ResponseEntity<Response> list(){
		Response response = services.list();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping(path = "/set/{name}")
	public ResponseEntity<Response> set(@PathVariable String name){
		Response response = services.set(name);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/map")
	public ResponseEntity<Response> map(){
		Response response = services.map();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/lambda")
	public ResponseEntity<Response> lambda(){
		Response response = services.lambda();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/parallelstream")
	public ResponseEntity<Response> parallelstream(){
		Response response = services.parallelstream();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
