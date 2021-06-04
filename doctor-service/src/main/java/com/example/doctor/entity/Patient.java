package com.example.doctor.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

	private Long nss; 
	private String name;
	private String lastName;
	private Long age;
	private LocalDate dayOfBirth;
	private Long idDoctor;

}
