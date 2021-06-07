package com.example.doctors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.doctors.entity.Doctor;

@Repository
public interface RepositoryDoc extends JpaRepository<Doctor, Long>{

	Doctor findDoctorByNameAndLastName(String name, String lastName);

	Doctor findDoctorById(Long id);

	Doctor findDoctorByCedula(Long cedula);
	
}
