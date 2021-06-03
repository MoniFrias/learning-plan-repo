package com.example.patient.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.patient.entity.Patient;
import com.example.patient.entity.Response;
import com.example.patient.repository.RepositoryPatient;

@Service
public class Services {
	
	@Autowired
	RepositoryPatient repository;
	private Pattern pattern;
    private Matcher matcher;
    
	
	private Response errorResponse(String message) {
		return new Response(false, message, null);
	}

	public Response save(Patient patient) {
		Response response = new Response();		
		pattern = Pattern.compile("[0-9]{9,9}");
		matcher = pattern.matcher(Long.toString(patient.getNss()));
		if(matcher.matches()) {
			Patient patientFound = repository.findPatientByNss(patient.getNss());
			if(patientFound == null) {
				response.setData(repository.save(patient));
				return response;
			}
			return errorResponse("Already there are a patient with that NSS");
		}
		return errorResponse("NSS needs to be lenght 9");
	}

	public Response saveMass(List<Patient> listPatient) {
		Response response = new Response();
			response.setData(repository.saveAll(listPatient));
			return response;		
	}

	public Response findAll() {
		Response response = new Response();
		List<Patient> listPatient = repository.findAll();
		if(!listPatient.isEmpty()) {
			response.setData(listPatient);
			return response;
		}				
		return errorResponse("There aren't patients");
	}

	public Response findById(Long id) {
		Response response = new Response();
		if(id != null && id > 0) {
			Patient patient = repository.findPatientById(id);
			if(patient != null) {
				response.setData(patient);
				return response;
			}
			return errorResponse("There aren't patients with that ID");
		}
		return errorResponse("ID can't be null or zero");
	}
	
	public Response findByIdDoctor(Long id) {
		Response response = new Response();
		if(id != null && id > 0) {
			List<Patient> patientList = repository.findPatientByIdDoctor(id);
			if(!patientList.isEmpty()) {
				response.setData(patientList);
				return response;
			}
			return errorResponse("There aren't patients with that IdDoctor");
		}
		return errorResponse("ID can't be null or zero");
	}

	public Response findByNameLastName(String name, String lastName) {
		Response response = new Response();
		Patient patient =  repository.findPatientByNameAndLastName(name, lastName);
		if(patient != null) {
			response.setData(patient);
			return response;
		}
		return errorResponse("There aren't patients");
	}

	public Response update(Patient patient, Long id) {
		Response response = new Response();
		if(id != null && id > 0) {
			Patient patienFound = repository.findPatientById(id);
			if(patienFound != null) {
				patienFound.setName(patient.getName());
				patienFound.setLastName(patient.getLastName());
				patienFound.setAge(patient.getAge());
				patienFound.setBloodType(patient.getBloodType());
				response.setData(repository.save(patienFound));
				return response;
			}
			return errorResponse("There is no patient with that ID");
		}
		return errorResponse("Id can't be null or zero");
	}

	public Response deleteById(Long id) {
		Response response = new Response();
		if(id != null && id > 0) {
			Patient patienFound = repository.findPatientById(id);
			if(patienFound != null) {
				repository.deleteById(id);
				return response;
			}
			return errorResponse("There is no patient with that ID");
		}		
		return errorResponse("Id can't be null or zero");
	}

	

}
