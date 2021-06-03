package com.example.patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.patient.entity.Patient;

@Repository
public interface RepositoryPatient extends JpaRepository<Patient, Long> {

	Patient findPatientById(Long id);

	List<Patient> findPatientByIdDoctor(Long id);

}
