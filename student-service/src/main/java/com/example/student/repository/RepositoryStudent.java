package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student.entity.Student;

@Repository
public interface RepositoryStudent extends JpaRepository<Student, Long> {

	Student findStudentById(Long id);

}
