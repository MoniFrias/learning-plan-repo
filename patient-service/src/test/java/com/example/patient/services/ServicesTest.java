package com.example.patient.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.example.patient.entity.Patient;
import com.example.patient.entity.ValidationException;
import com.example.patient.repository.RepositoryPatient;

@ExtendWith(MockitoExtension.class)
class ServicesTest {

	@InjectMocks
	Services services;
	@Mock
	RepositoryPatient repository;
	
	@Test
	public void saveTest() {
		Patient patient = new Patient(1L, 12345678L, "name", "lastName",
				1L, LocalDate.of(1996, 11, 10), 1L, LocalTime.of(13, 30), LocalDate.of(2021, 11, 10));
		BindingResult validationResult = new BeanPropertyBindingResult(patient, "");
		when(repository.findPatientByNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		assertTrue(services.save(patient, validationResult).isResult());
	}
	
	@Test
	public void saveTest_patientFoundElse() {
		Patient patient = new Patient(1L, 12345678L, "name", "lastName",
				1L, LocalDate.of(1996, 11, 10), 1L, LocalTime.of(13, 30), LocalDate.of(2021, 11, 10));
		BindingResult validationResult = new BeanPropertyBindingResult(patient, "");
		when(repository.findPatientByNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(patient);
		assertThrows(ValidationException.class, () -> services.save(patient, validationResult));
	}
	
	@Test
	public void saveTest_validateElse() {
		Patient patient = new Patient(1L, 123L, "name", "lastName",
				1L, LocalDate.of(1996, 11, 10), 1L, LocalTime.of(13, 30), LocalDate.of(2021, 11, 10));
		BindingResult validationResult = new BeanPropertyBindingResult(patient, "");
		assertThrows(ValidationException.class, () -> services.save(patient, validationResult));
	}
	
	@Test
	public void saveMassTest() {
		Patient patient = new Patient(1L, 12345678L, "name", "lastName",1L, 
                LocalDate.of(1996, 11, 10), 1L, LocalTime.of(13, 30), LocalDate.of(2021, 11, 10));
		List<Patient> listPatient = new ArrayList<>();
		listPatient.add(patient);
		when(repository.findPatientByNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(patient);
		when(repository.save(Mockito.any())).thenReturn(patient);
		assertTrue(services.saveMass(listPatient).isResult());
	}
	
	@Test
	public void saveMassTest_listPatientNewElse() {
		Patient patient = new Patient(1L, 12345678L, "name", "lastName",1L, 
                LocalDate.of(1996, 11, 10), 1L, LocalTime.of(13, 30), LocalDate.of(2021, 11, 10));
		List<Patient> listPatient = new ArrayList<>();
		listPatient.add(patient);
		when(repository.findPatientByNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		assertThrows(ValidationException.class, () -> services.saveMass(listPatient));
	}
	
	@Test
	public void findAllTest() {
		List<Patient> listPatient = new ArrayList<>();
		listPatient.add(new Patient(1L, 12345678L, "name", "lastName",1L, 
                LocalDate.of(1996, 11, 10), 1L, LocalTime.of(13, 30), LocalDate.of(2021, 11, 10)));
		when(repository.findAll()).thenReturn(listPatient);
		assertTrue(services.findAll().isResult());
	}
	
	@Test
	public void findAllTest_Else() {
		List<Patient> listPatient=new ArrayList<>();
		when(repository.findAll()).thenReturn(listPatient);
		assertThrows(ValidationException.class, () -> services.findAll());
	}
	
	@Test
	public void findByIdTest() {
		Patient patient=new Patient(1L, 12345678L, "name", "lastName",1L, 
                LocalDate.of(1996, 11, 10), 1L, LocalTime.of(13, 30), LocalDate.of(2021, 11, 10));
		when(repository.findPatientById(Mockito.anyLong())).thenReturn(patient);
		assertEquals("success", services.findById(1L).getMessage());
	}

	@Test
	public void findByIdTest_PatientElse() {
		when(repository.findPatientById(Mockito.anyLong())).thenReturn(null);
		assertThrows(ValidationException.class, () -> services.findById(1L));
	}
	
	@Test
	public void findByIdTest_IDNull() {
		assertThrows(ValidationException.class, () -> services.findById(null));
	}
	
	@Test
	public void findByIdTest_IDZero() {
		assertThrows(ValidationException.class, () -> services.findById(0L));
	}
	
	@Test
	public void findByIdDoctorTest() {
		List<Patient> listPatient=new ArrayList<>();
		listPatient.add(new Patient(1L, 12345678L, "name", "lastName",1L, 
                LocalDate.of(1996, 11, 10), 1L, LocalTime.of(13, 30), LocalDate.of(2021, 11, 10)));
		when(repository.findPatientByIdDoctor(Mockito.anyLong())).thenReturn(listPatient);
		assertEquals("success", services.findByIdDoctor(1L).getMessage());
	}
	
	@Test
	public void findByIdDoctorTest_listPatientElse() {
		List<Patient> listPatient=new ArrayList<>();
		when(repository.findPatientByIdDoctor(Mockito.anyLong())).thenReturn(listPatient);
		assertEquals("Fail", services.findByIdDoctor(1L).getMessage());
	}
	
	@Test
	public void findByIdDoctorTest_IDNull() {
		assertThrows(ValidationException.class, () -> services.findByIdDoctor(null));
	}
	
	@Test
	public void findByIdDoctorTest_IDZero() {
		assertThrows(ValidationException.class, () -> services.findByIdDoctor(0L));
	}
	
	@Test
	public void updateTest() {
		Patient patient=new Patient(1L, 12345678L, "name", "lastName",1L, 
                LocalDate.of(1996, 11, 10), 1L, LocalTime.of(13, 30), LocalDate.of(2021, 11, 10));
		when(repository.findPatientById(Mockito.anyLong())).thenReturn(patient);
		assertTrue(services.update(patient, 1L).isResult());
	}
	
	@Test
	public void updateTest_patientFoundElse() {
		Patient patient=null;
		when(repository.findPatientById(Mockito.anyLong())).thenReturn(patient);
		assertThrows(ValidationException.class, () -> services.update(patient, 1L));
	}
	
	@Test
	public void updateTest_IDNull() {
		Patient patient=null;
		assertThrows(ValidationException.class, () -> services.update(patient, null));
	}
	
	@Test
	public void updateTest_IDZero() {
		Patient patient=null;
		assertThrows(ValidationException.class, () -> services.update(patient, 0L));
	}
	
	@Test
	public void deleteByIdTest() {
		Patient patient=new Patient(1L, 12345678L, "name", "lastName",1L, 
                LocalDate.of(1996, 11, 10), 1L, LocalTime.of(13, 30), LocalDate.of(2021, 11, 10));
		when(repository.findPatientById(Mockito.anyLong())).thenReturn(patient);
		assertEquals("success",services.deleteById(1L).getMessage());
	}
	
	@Test
	public void deleteByIdTest_patientFoundElse() {
		when(repository.findPatientById(Mockito.anyLong())).thenReturn(null);
		assertThrows(ValidationException.class, () -> services.deleteById(1L));
	}
	
	@Test
	public void deleteByIdTest_IDNull() {
		assertThrows(ValidationException.class, () -> services.deleteById(null));
	}
	
	@Test
	public void deleteByIdTest_IDZero() {
		assertThrows(ValidationException.class, () -> services.deleteById(0L));
	}
	
}
