package com.example.patient.services;

import java.time.LocalDate;
import java.time.LocalTime;
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
	private Pattern patternCedula, patternTime, patternDate;
	private Matcher matcherCedula , matcherTime, matcherDate, matcherDateBirth;
	
	private Patient foundPatientByNameAndLastName(String name, String lastName) {
		Patient patientFound = repository.findPatientByNameAndLastName(name,lastName);
		return patientFound;
	}
	
	private boolean validation(Long Nss, LocalTime time, LocalDate date, LocalDate dateBirth) {
		patternCedula = Pattern.compile("[1-9]{8,8}");
		matcherCedula = patternCedula.matcher(Long.toString(Nss));
		patternTime = Pattern.compile("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
		matcherTime = patternTime.matcher(time.toString());
		patternDate = Pattern.compile("^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"  + 
				                      "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
		matcherDate = patternDate.matcher(date.toString());
		matcherDateBirth = patternDate.matcher(dateBirth.toString());
		if(matcherCedula.matches() && matcherTime.matches() && matcherDate.matches() && matcherDateBirth.matches()) {
			return true;
		}else {
			return false;
		}
	}
	
	public Response save(Patient patient,BindingResult validationResult) {
		Response response = new Response();
		Patient patientFound = foundPatientByNameAndLastName(patient.getName(), patient.getLastName());
		boolean validate = validation(patient.getNss(), patient.getAppointmentTime(), patient.getAppointmentDate(), patient.getDayOfBirth());
		if(validate && !validationResult.hasErrors()) {
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
				response.setResult(false);
				response.setMessage("Fail");
				return response;
			}
		}else {
			throw new ValidationException("can't be null or zero");
		}	
	}
	
	private Patient updatePatient(Patient patientGot, Patient patientFound) {
		patientFound.setNss(patientGot.getNss());
		patientFound.setName(patientGot.getName());
		patientFound.setLastName(patientGot.getLastName());
		patientFound.setAge(patientGot.getAge());
		patientFound.setDayOfBirth(patientGot.getDayOfBirth());
		patientFound.setIdDoctor(patientGot.getIdDoctor());
		patientFound.setAppointmentTime(patientGot.getAppointmentTime());
		patientFound.setAppointmentDate(patientGot.getAppointmentDate());
		return patientFound;
	}

	public Response update(Patient patient, Long id) {
		Response response = new Response();
		if(id != null && id > 0) {
			Patient patientFound = repository.findPatientById(id);
			if(patientFound != null) {
				Patient patientNew = updatePatient(patient, patientFound);
				response.setData(repository.save(patientNew));
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
