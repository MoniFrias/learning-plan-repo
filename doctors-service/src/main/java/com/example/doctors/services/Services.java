package com.example.doctors.services;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.doctors.entity.Doctor;
import com.example.doctors.entity.Patient;
import com.example.doctors.entity.Response;
import com.example.doctors.entity.ValidationException;
import com.example.doctors.repository.RepositoryDoc;


@Service
public class Services {

	@Autowired
	RepositoryDoc repository;
	@Autowired
	WebClient webClient;
	@Value("${patientFindByIdDoctor}")
	private String patientFindByIdDoctor;
	private Pattern pattern;
    private Matcher matcher;

	private Doctor foundDoctorByNameAndLastName(String name, String lastName) {
		Doctor doctorFound = repository.findDoctorByNameAndLastName(name,lastName);
		return doctorFound;
	}	
	
	public Response save(Doctor doctor,BindingResult validResult) {
		Response response = new Response();
		Doctor doctorFound = foundDoctorByNameAndLastName(doctor.getName(), doctor.getLastName());
		pattern = Pattern.compile("[1-9]{8,8}");
		matcher = pattern.matcher(Long.toString(doctor.getCedula()));
		System.out.println(validResult);
		if(matcher.matches() && !validResult.hasErrors()) {
			if (doctorFound == null) {
				response.setData(repository.save(doctor));
				return response;
			}else {
				throw new ValidationException("Already there is a Doctor with that name");
			}
		}else {
			throw new ValidationException("Some data is wrong");
		}
	}

	public Response saveMass(List<Doctor> listDoctor, BindingResult validResult) {
		Response response = new Response();
		List<Doctor> listDoctorNew = listDoctor.stream().map(doctor ->{
			Doctor doct=null;
			Doctor doctorFound= foundDoctorByNameAndLastName(doctor.getName(), doctor.getLastName());
			if(doctorFound == null ) {
				doct = repository.save(doctor);
			}else {
				System.out.println("Ya existe");
			}
			return doct;
		}).filter(Objects::nonNull).collect(Collectors.toList());
		
		if(!listDoctorNew.isEmpty()) {
			response.setData(listDoctorNew);
			return response;
		}else {
			throw new ValidationException("The doctors have already been saved");
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Patient> patientFindByIdDoctor(Long id){
		return (List<Patient>) webClient.get()
				.uri(patientFindByIdDoctor+id)
				.retrieve()
				.bodyToMono(Response.class)
				.block()
				.getData();
	}

	public Response findAll() {
		Response response = new Response();		
		List<Doctor> listDoctors = repository.findAll();
		if(!listDoctors.isEmpty()) {
			List<Doctor> listDoctorNew = listDoctors.stream().map(doctor ->{
				List<Patient> listPatient = patientFindByIdDoctor(doctor.getId());
				if (listPatient != null && !listPatient.isEmpty()) {
					doctor.setListPatient(listPatient);					
				}else {
					doctor.setListPatient(null);					
				}
				return doctor;
			}).filter(Objects::nonNull).collect(Collectors.toList());
			
			if (!listDoctorNew.isEmpty()) {
				response.setData(listDoctorNew);
				return response;
			}else {
				throw new ValidationException("No saved doctors empty");
			}
		}else {
			throw new ValidationException("No saved doctors");
		}
	}

	public Response findById(Long id) {
		Response response = new Response();
		if (id != null && id > 0) {
			Doctor doctor = repository.findDoctorById(id);
			if (doctor != null) {
				List<Patient> listPatient = patientFindByIdDoctor(doctor.getId());
				if (listPatient != null && !listPatient.isEmpty()) {
					doctor.setListPatient(listPatient);
					response.setData(doctor);
					return response;
				}else {
					response.setData(doctor);
					return response;				
				}
			} else {
				throw new ValidationException("No saved doctors with that ID");
			}
		} else {
			throw new ValidationException("ID can't be null or zero");
		}
	}

	public Response findByNameAndLastName(String name, String lastname) {
		Response response = new Response();
		Doctor doctorFound= foundDoctorByNameAndLastName(name, lastname);
		if(doctorFound != null) {
			List<Patient> listPatient = patientFindByIdDoctor(doctorFound.getId());
			if (listPatient != null && !listPatient.isEmpty()) {
				doctorFound.setListPatient(listPatient);
				response.setData(doctorFound);
				return response;
			}else {
				response.setData(doctorFound);
				return response;				
			}
		}else {
			throw new ValidationException("No saved doctors with that Name");
		}
	}

	public Response findByCedula(Long cedula) {
		Response response = new Response();
		Doctor doctorFound = repository.findDoctorByCedula(cedula);
		if(doctorFound != null) {
			List<Patient> listPatient = patientFindByIdDoctor(doctorFound.getId());
			if (listPatient != null && !listPatient.isEmpty()) {
				doctorFound.setListPatient(listPatient);
				response.setData(doctorFound);
				return response;
			}else {
				response.setData(doctorFound);
				return response;				
			}
		}else {
			throw new ValidationException("No saved doctors with that Cedula");
		}
	}

	public Response update(Doctor doctor, Long id) {
		Response response = new Response();
		if (id != null && id > 0) {
			Doctor doctorFound = repository.findDoctorById(id);
			if(doctorFound != null) {
				doctorFound.setCedula(doctor.getCedula());
				doctorFound.setName(doctor.getName());
				doctorFound.setLastName(doctor.getLastName());
				doctorFound.setSpeciality(doctor.getSpeciality());
				response.setData(repository.save(doctorFound));
				return response;
			}else {
				throw new ValidationException("No saved doctors with that ID");
			}
		}else {
			throw new ValidationException("ID can't be null or zero");
		}
	}

	public Response deleteById(Long id) {
		Response response = new Response();
		if (id != null && id > 0) {
			Doctor doctorFound = repository.findDoctorById(id);
			if(doctorFound != null) {
				repository.deleteById(id);
				return response;
			}else {
				throw new ValidationException("No saved doctors with that ID");
			}
		}else {
			throw new ValidationException("ID can't be null or zero");
		}
	}

}
