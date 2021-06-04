package com.example.doctor.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

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
	@Column(name = "name")
	private String name;
	@Column(name = "lastName")
	private String lastName;
	@Column(name = "speciality")
	private String speciality;
	
	@Transient
	List<Patient> listPatient;

}
 