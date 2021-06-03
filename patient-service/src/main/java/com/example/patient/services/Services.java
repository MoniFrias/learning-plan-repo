package com.example.patient.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.patient.entity.Patient;
import com.example.patient.entity.Response;
import com.example.patient.repository.RepositoryPatient;

@Service
public class Services {

	@Autowired
	RepositoryPatient repository;
	
	private Response errorResponse(String message) {
		return new Response(false, message, null);
	}
	
	public Response save(Patient patient) {
		Response response = new Response();
		response.setData(repository.save(patient));
		return response;
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
		}else {
			return errorResponse("empty");
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
				return errorResponse("empty");
			}
		}else {
			return errorResponse("can't be null or zero");
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
				return errorResponse("empty");
			}
		}else {
			return errorResponse("can't be null or zero");
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
				return errorResponse("empty");
			}
		}else {
			return errorResponse("can't be null or zero");
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
				return errorResponse("empty");
			}
		}else {
			return errorResponse("can't be null or zero");
		}
	}

	

}
