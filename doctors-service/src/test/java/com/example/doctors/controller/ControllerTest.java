package com.example.doctors.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.example.doctors.entity.Doctor;
import com.example.doctors.services.Services;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

	@InjectMocks
	Controller controller;
	@Mock
	Services services;
	
	@Test
	public void saveTest() {
		Doctor doctor = null;
		BindingResult validResult = null;
		assertEquals(HttpStatus.OK, controller.save(doctor, validResult).getStatusCode());
	}
	
	@Test
	public void saveMassTest() {
		List<Doctor> listDoctor = null;
		BindingResult validResult = null;
		assertEquals(HttpStatus.OK, controller.saveMass(listDoctor, validResult).getStatusCode());
	}
	
	@Test
	public void findAllTest() {
		assertEquals(HttpStatus.FOUND, controller.findAll().getStatusCode());
	}

	@Test
	public void findByIdTest() {
		assertEquals(HttpStatus.FOUND, controller.findById(1L).getStatusCode());
	}
	
	@Test
	public void findByNameAndLastNameTest() {
		assertEquals(HttpStatus.FOUND, controller.findByNameAndLastName("Luis","Lopez").getStatusCode());
	}
	
	@Test
	public void findByCedulaTest() {
		assertEquals(HttpStatus.FOUND, controller.findByCedula(1L).getStatusCode());
	}
	
	@Test
	public void updateTest() {
		Doctor doctor = null;
		assertEquals(HttpStatus.OK, controller.update(doctor,1L).getStatusCode());
	}
	
	@Test
	public void deleteByIdTest() {
		assertEquals(HttpStatus.OK, controller.deleteById(1L).getStatusCode());
	}
}
