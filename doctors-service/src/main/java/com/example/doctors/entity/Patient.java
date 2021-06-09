package com.example.doctors.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient {

	private Long nss; 
	private String name;
	private String lastName;
	private Long age;
	private LocalDate dayOfBirth;
	private Long idDoctor;
}
