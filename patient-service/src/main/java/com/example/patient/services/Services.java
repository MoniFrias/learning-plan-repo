package com.example.patient.services;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.patient.entity.Patient;
import com.example.patient.entity.Response;
import com.example.patient.entity.ValidationException;
import com.example.patient.repository.RepositoryPatient;

@Service
public class Services {

	@Autowired
	RepositoryPatient repository;
	private Pattern pattern;
	private Matcher matcher;
	
	private Patient foundPatientByNameAndLastName(String name, String lastName) {
		Patient patientFound = repository.findPatientByNameAndLastName(name,lastName);
		return patientFound;
	}
	
	public Response save(Patient patient,BindingResult validationResult) {
		Response response = new Response();
		Patient patientFound = foundPatientByNameAndLastName(patient.getName(), patient.getLastName());
		pattern = Pattern.compile("[1-9]{8,8}");
		matcher = pattern.matcher(Long.toString(patient.getNss()));
		if(matcher.matches() && !validationResult.hasErrors()) {
			if (patientFound == null) {
				response.setData(repository.save(patient));
				return response;
			}else {
				throw new ValidationException("Already there is a Patient with that name");
			}
		}else {
			throw new ValidationException("Some data is wrong");
		}
	}

	public Response saveMass(List<Patient> listPatient) {
		Response response = new Response();
		List<Patient> listPatientNew = listPatient.stream().map(patient ->{
			Patient patientSave = null;
			Patient patientFound = foundPatientByNameAndLastName(patient.getName(), patient.getLastName());
			if(patientFound != null) {
				patientSave = repository.save(patient);
			}else {
				System.out.println("Ya existe");
			}return patientSave;
		}).filter(Objects::nonNull).collect(Collectors.toList());
		
		if(!listPatientNew.isEmpty()) {
			response.setData(listPatientNew);
			return response;
		}else {
			throw new ValidationException("The Patients have already been saved");
		}
	}

	public Response findAll() {
		Response response = new Response();
		List<Patient> listPatient = repository.findAll();
		if(!listPatient.isEmpty()) {
			response.setData(listPatient);
			return response;
		}else {
			throw new ValidationException("No saved Patients");
		}
	}

	public Response findById(Long id) {
		Response response = new Response();
		if(id != null && id > 0) {
			Patient patient = repository.findPatientById(id);
			if(patient != null) {
				response.setData(patient);
				return response;
			}else {
				throw new ValidationException("No saved Patients with that ID");
			}
		}else {
			throw new ValidationException("can't be null or zero");
		}
	}

	public Response findByIdDoctor(Long id) {
		Response response = new Response();
		if(id != null && id > 0) {
			List<Patient> listPatient = repository.findPatientByIdDoctor(id);
			if(!listPatient.isEmpty()) {
				response.setData(listPatient);
				return response;
			}else {
				throw new ValidationException("No saved Patients with that ID");
			}
		}else {
			throw new ValidationException("can't be null or zero");
		}	
	}

	public Response update(Patient patient, Long id) {
		Response response = new Response();
		if(id != null && id > 0) {
			Patient patientFound = repository.findPatientById(id);
			if(patientFound != null) {
				patientFound.setNss(patient.getNss());
				patientFound.setName(patient.getName());
				patientFound.setLastName(patient.getLastName());
				patientFound.setAge(patient.getAge());
				patientFound.setDayOfBirth(patient.getDayOfBirth());
				patientFound.setIdDoctor(patient.getIdDoctor());
				response.setData(repository.save(patientFound));
				return response;
			}else {
				throw new ValidationException("No saved Patients with that ID");
			}
		}else {
			throw new ValidationException("can't be null or zero");
		}
	}

	public Response deleteById(Long id) {
		Response response = new Response();
		if(id != null && id > 0) {
			Patient patientFound = repository.findPatientById(id);
			if(patientFound != null) {
				repository.deleteById(id);
				return response;
			}else {
				throw new ValidationException("No saved Patients with that ID");
			}
		}else {
			throw new ValidationException("can't be null or zero");
		}
	}

	

}
