package com.example.doctor.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "cedula")
	private Long cedula;	
	@Pattern(regexp = "[a-zA-Z]{4,10}")
	@Column(name = "name")
	private String name;
	@Column(name = "lastName")
	@Pattern(regexp = "[a-zA-Z]{4,10}")
	private String lastName;
	@Column(name = "speciality")
	@Pattern(regexp = "[a-zA-Z]{4,10}")
	private String speciality;
	
	@Transient
	List<Patient> listPatient;

}
 