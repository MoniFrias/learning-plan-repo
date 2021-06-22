package com.example.doctors.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.example.doctors.entity.Doctor;
import com.example.doctors.entity.Patient;
import com.example.doctors.entity.Response;
import com.example.doctors.entity.ValidationException;
import com.example.doctors.repository.RepositoryDoc;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ServicesTest {

	@InjectMocks
	Services services;
	@Mock
	RepositoryDoc repository;
	@Mock
	WebClient webClient;
	@Mock
	RequestHeadersUriSpec requestHeaderUriSpec;
	@Mock
	RequestHeadersSpec requestHeaderSpec;
	@Mock
	ResponseSpec responseSpec;
	
	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(services, "patientFindByIdDoctor", "");
	}
	
	@Test
	public void saveTest() {
		Doctor doctor = new Doctor(1L, 12345678L, "name", "lastName", 
				"speciality", new ArrayList<>());
		BindingResult validResult=new BeanPropertyBindingResult(doctor, "");
		assertTrue(services.save(doctor, validResult).isResult());
	}
	
	@Test
	public void saveDoctorFoundTest() {
		Doctor doctor = new Doctor(1L, 12345678L, "name", "lastName", "speciality", new ArrayList<>());
		BindingResult validResult=new BeanPropertyBindingResult(doctor, "");
		when(repository.findDoctorByNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(new Doctor());
		assertThrows(ValidationException.class, () -> services.save(doctor, validResult));
	}
	
	@Test
	public void saveDoesntMatchTest() {
		Doctor doctor = new Doctor(1L, 1L, "name", "lastName", "speciality", new ArrayList<>());
		BindingResult validResult=new BeanPropertyBindingResult(doctor, "");
		assertThrows(ValidationException.class, () -> services.save(doctor, validResult));
	}	
	
	@Test
	public void saveDoesntMatch2Test() {
		Doctor doctor = new Doctor(1L, 1L, "name33", "lastName33", "speciality33", new ArrayList<>());
		BindingResult validResult=new BeanPropertyBindingResult(doctor, "");
		assertThrows(ValidationException.class, () -> services.save(doctor, validResult));
	}	
	
	@Test
	public void saveMassTest() {
		List<Doctor> litsDoctor = new ArrayList<>(); 
		litsDoctor.add(new Doctor(1L, 12345678L, "name", "lastName", 
				"speciality", new ArrayList<>()));
		BindingResult validResult=new BeanPropertyBindingResult(litsDoctor, "");
	
		when(repository.findDoctorByNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		Doctor doctor=new Doctor(1L, 12345678L, "name", "lastName", "speciality", new ArrayList<>());
		when(repository.save(Mockito.any())).thenReturn(doctor);
		assertTrue(services.saveMass(litsDoctor, validResult).isResult());
	}
	
	@Test
	public void saveMassThrowTest() {
		List<Doctor> litsDoctor = new ArrayList<>(); 
		litsDoctor.add(new Doctor(1L, 12345678L, "name", "lastName", 
				"speciality", new ArrayList<>()));
		BindingResult validResult=new BeanPropertyBindingResult(litsDoctor, "");
		Doctor doctor=new Doctor(1L, 12345678L, "name", "lastName", "speciality", new ArrayList<>());
		when(repository.findDoctorByNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(doctor);
		
		assertThrows(ValidationException.class, () -> services.saveMass(litsDoctor, validResult));
	}
	
	@Test
	public void findAllTest() {
		List<Doctor> listDoctor = new ArrayList<>();
		listDoctor.add(new Doctor(1L, 12345678L, "name", "lastName",
				"speciality", new ArrayList<>()));
		when(repository.findAll()).thenReturn(listDoctor);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
		List<Patient> listPtient= new ArrayList<>();
		listPtient.add(new Patient(1L, 1L, "name", "lastName", 1L, LocalDate.now(), 1L));
		Mono<Response> monoResponse=Mono.just(new Response(true, "", listPtient));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertTrue(services.findAll().isResult());
	}
	
	@Test
	public void findAllTestPatientFoundByDoctorElse() {
		List<Doctor> listDoctor = new ArrayList<>();
		listDoctor.add(new Doctor(1L, 12345678L, "name", "lastName",
				"speciality", new ArrayList<>()));
		when(repository.findAll()).thenReturn(listDoctor);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
		List<Patient> listPtient= new ArrayList<>();
		Mono<Response> monoResponse=Mono.just(new Response(true, "", listPtient));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertThrows(ValidationException.class, () -> services.findAll());
	}
	
	@Test
	public void findAllTest_ListDoctorNewElse() {
		List<Doctor> listDoctor = new ArrayList<>();
		listDoctor.add(new Doctor(1L, 12345678L, "name", "lastName",
				"speciality", new ArrayList<>()));
		when(repository.findAll()).thenReturn(listDoctor);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
		List<Patient> listPtient= new ArrayList<>();
		Mono<Response> monoResponse=Mono.just(new Response(true, "", listPtient));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertThrows(ValidationException.class, () -> services.findAll());
	}
	
	@Test
	public void findAllTestEMPTY() {
		List<Doctor> listDoctor = new ArrayList<>();		
		when(repository.findAll()).thenReturn(listDoctor);
		assertThrows(ValidationException.class, () -> services.findAll());
	}

	@Test
	public void findByIdTest() {
		Doctor doctor = new Doctor(1L, 12345678L, "name", "lastName",
				"speciality", new ArrayList<>());		
		when(repository.findDoctorById(Mockito.anyLong())).thenReturn(doctor);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
		List<Patient> listPtient= new ArrayList<>();
		listPtient.add(new Patient(1L, 1L, "name", "lastName", 1L, LocalDate.now(), 1L));
		Mono<Response> monoResponse=Mono.just(new Response(true, "", listPtient));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertTrue(services.findById(1L).isResult());
	}
	
	@Test
	public void findByIdTest_patientFindByIdDoctorELSE() {
		Doctor doctor = new Doctor(1L, 12345678L, "name", "lastName",
				"speciality", new ArrayList<>());		
		when(repository.findDoctorById(Mockito.anyLong())).thenReturn(doctor);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
		List<Patient> listPtient= new ArrayList<>();
		Mono<Response> monoResponse=Mono.just(new Response(true, "", listPtient));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertTrue(services.findById(1L).isResult());
	}
	
	@Test
	public void findByIdTest_doctorNull() {
		Doctor doctor = null;	
		when(repository.findDoctorById(Mockito.anyLong())).thenReturn(doctor);
		assertThrows(ValidationException.class, () -> services.findById(1L));
	}
	
	@Test
	public void findByIdTest_IdNull() {
		assertThrows(ValidationException.class, () -> services.findById(null));
	}
	
	@Test
	public void findByIdTest_IdZero() {
		assertThrows(ValidationException.class, () -> services.findById(0L));
	}
	
	@Test
	public void findByNameAndLastNameTest() {
		Doctor doctor = new Doctor(1L, 12345678L, "name", "lastName",
				"speciality", new ArrayList<>());		
		when(repository.findDoctorByNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(doctor);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
		List<Patient> listPtient= new ArrayList<>();
		listPtient.add(new Patient(1L, 1L, "name", "lastName", 1L, LocalDate.now(), 1L));
		Mono<Response> monoResponse=Mono.just(new Response(true, "", listPtient));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertTrue(services.findByNameAndLastName("name", "lastname").isResult());
	}
	
	@Test
	public void findByNameAndLastNameTest_ListPatientNull() {
		Doctor doctor = new Doctor(1L, 12345678L, "name", "lastName",
				"speciality", new ArrayList<>());		
		when(repository.findDoctorByNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(doctor);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
		List<Patient> listPtient= new ArrayList<>();
		Mono<Response> monoResponse=Mono.just(new Response(true, "", listPtient));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertTrue(services.findByNameAndLastName("name", "lastname").isResult());
	}
	
	@Test
	public void findByNameAndLastNameTest_doctorFoundNull() {
		Doctor doctor = null;	
		when(repository.findDoctorByNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(doctor);
		assertThrows(ValidationException.class, () -> services.findByNameAndLastName("name", "lastname"));
	}
	
	@Test
	public void findByCedulaTest() {
		Doctor doctor = new Doctor(1L, 12345678L, "name", "lastName",
				"speciality", new ArrayList<>());		
		when(repository.findDoctorByCedula(Mockito.anyLong())).thenReturn(doctor);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
		List<Patient> listPtient= new ArrayList<>();
		listPtient.add(new Patient(1L, 1L, "name", "lastName", 1L, LocalDate.now(), 1L));
		Mono<Response> monoResponse=Mono.just(new Response(true, "", listPtient));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertTrue(services.findByCedula(1L).isResult());
	}
	
	@Test
	public void findByCedulaTest_listPatientELSE() {
		Doctor doctor = new Doctor(1L, 12345678L, "name", "lastName",
				"speciality", new ArrayList<>());		
		when(repository.findDoctorByCedula(Mockito.anyLong())).thenReturn(doctor);
		when(webClient.get()).thenReturn(requestHeaderUriSpec);
		when(requestHeaderUriSpec.uri(Mockito.anyString())).thenReturn(requestHeaderSpec);
		when(requestHeaderSpec.retrieve()).thenReturn(responseSpec);
		List<Patient> listPtient= new ArrayList<>();
		Mono<Response> monoResponse=Mono.just(new Response(true, "", listPtient));
		when(responseSpec.bodyToMono(Response.class)).thenReturn(monoResponse);
		assertTrue(services.findByCedula(1L).isResult());
	}
	
	@Test
	public void findByCedulaTest_doctorFoundELSE() {
		Doctor doctor = null;	
		when(repository.findDoctorByCedula(Mockito.anyLong())).thenReturn(doctor);
		assertThrows(ValidationException.class, () -> services.findByCedula(1L));
	}
	
	@Test
	public void updateTest() {
		Doctor doctor = new Doctor(1L, 12345678L, "name", "lastName",
		                           "speciality", new ArrayList<>());		
		when(repository.findDoctorById(Mockito.anyLong())).thenReturn(doctor);
		assertTrue(services.update(doctor,1L).isResult());
	}
	
	@Test
	public void updateTest_Else() {
		Doctor doctor = null;		
		when(repository.findDoctorById(Mockito.anyLong())).thenReturn(doctor);
		assertThrows(ValidationException.class, () -> services.update(doctor,1L));
	}
	
	@Test
	public void updateTest_IDNull() {
		Doctor doctor = null;
		assertThrows(ValidationException.class, () -> services.update(doctor,null));
	}
	
	@Test
	public void updateTest_IDZero() {
		Doctor doctor = null;
		assertThrows(ValidationException.class, () -> services.update(doctor,0L));
	}
	
	@Test
	public void deleteByIdTest() {
		Doctor doctor = new Doctor(1L, 12345678L, "name", "lastName",
				                   "speciality", new ArrayList<>());
		when(repository.findDoctorById(Mockito.anyLong())).thenReturn(doctor);
		assertTrue(services.deleteById(1L).isResult());
	}
	
	@Test
	public void deleteByIdTest_DoctorFoundElse() {
		Doctor doctor = null;
		when(repository.findDoctorById(Mockito.anyLong())).thenReturn(doctor);
		assertThrows(ValidationException.class, () -> services.deleteById(1L));
	}
	
	@Test
	public void deleteByIdTest_IDZero() {
		assertThrows(ValidationException.class, () -> services.deleteById(0L));
	}
	
	@Test
	public void deleteByIdTest_IDNull() {
		assertThrows(ValidationException.class, () -> services.deleteById(null));
	}
}
