package com.example.student.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentSubject {

	private String name;
	private String lastName;
	private Long idStudent;
	private Long grade;
	
	private List<Subject> listSubject;

}
