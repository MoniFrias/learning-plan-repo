package com.example.student.entity;

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
public class Student {

	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "lastName")
	private String lastName;
	@Column(name = "idStudent")
	private Long idStudent;
	@Column(name = "grade")
	private Long grade;
	
	@Transient
	List<Subject> listSubject;
}
