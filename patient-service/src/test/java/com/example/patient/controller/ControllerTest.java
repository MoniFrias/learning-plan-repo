package com.example.patient.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.example.patient.entity.Patient;
import com.example.patient.services.Services;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

	@InjectMocks
	Controller controller;
	@Mock
	Services services;
	
	@Test
	public void saveTest() {
		Patient patient = null;
		BindingResult validationResult = null;
		assertEquals(HttpStatus.OK, controller.save(patient, validationResult).getStatusCode());
	}
	
	@Test
	public void saveMassTest() {
		List<Patient> listPatient=null;
		assertEquals(HttpStatus.OK, controller.saveMass(listPatient).getStatusCode());
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
	public void findByIdDoctorTest() {
		assertEquals(HttpStatus.FOUND, controller.findByIdDoctor(1L).getStatusCode());
	}
	 
	@Test
	public void updateTest() {
		Patient patient = null;
		assertEquals(HttpStatus.OK, controller.update(patient, 1L).getStatusCode());
	}
	
	@Test
	public void deleteByIdTest() {
		assertEquals(HttpStatus.OK, controller.deleteById(1L).getStatusCode());
	}
}
