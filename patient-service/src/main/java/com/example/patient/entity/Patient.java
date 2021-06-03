package com.example.patient.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "nss")
	private Long nss; 
	@Column(name = "name")
	private String name;
	@Column(name = "lastName")
	private String lastName;
	@Column(name = "age")
	private Long age;
	@Column(name = "dayOfBirth")
	private LocalDate dayOfBirth;
	@Column(name = "idDoctor")
	private Long idDoctor;
	
	
	

}
