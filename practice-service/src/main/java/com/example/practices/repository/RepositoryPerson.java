package com.example.practices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practices.entity.Person;

@Repository
public interface RepositoryPerson extends JpaRepository<Person, Long>{

	Person findPersonById(Long id);

}
