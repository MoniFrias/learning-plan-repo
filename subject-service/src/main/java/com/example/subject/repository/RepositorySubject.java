package com.example.subject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.subject.entity.Subject;

@Repository
public interface RepositorySubject extends JpaRepository<Subject, Long> {

	Subject findSubjectById(Long id);

	List<Subject> findSubjectByGradeSubject(Long idGrade);

}
