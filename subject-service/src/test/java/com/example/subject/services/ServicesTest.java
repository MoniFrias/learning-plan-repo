package com.example.subject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.subject.entity.Subject;
import com.example.subject.repository.RepositorySubject;

@ExtendWith(MockitoExtension.class)
class ServicesTest {

	@InjectMocks
	Services services;
	@Mock
	RepositorySubject repository;
	
	@Test
	public void testSave() {
		Subject subject= new Subject();
		assertEquals("success", services.save(subject).getMessage());
	}
	
	@Test
	public void testSaveMass() {
		List<Subject> subjectList= new ArrayList<>();
		Subject subject= new Subject(1L, "name", "nameTeacher", 1L);
		subjectList.add(subject);
		when(repository.save(Mockito.any())).thenReturn(subject);
		assertEquals("success", services.saveMass(subjectList).getMessage());
	}

	@Test
	public void testFindAll() {
		List<Subject> subjectList= new ArrayList<>();
		subjectList.add(new Subject(1L, "name", "nameTeacher", 1L));	
		when(repository.findAll()).thenReturn(subjectList);
		assertEquals("success", services.findAll().getMessage());
	}
	
	@Test
	public void testFindAll_Empty() {
		List<Subject> subjectList= new ArrayList<>();	
		when(repository.findAll()).thenReturn(subjectList);
		assertFalse(services.findAll().isResult());
	}
	
	@Test
	public void testFindById() {
		Subject subject = new Subject(1L, "name", "nameTeacher", 1L);	
		when(repository.findSubjectById(Mockito.anyLong())).thenReturn(subject);
		assertEquals("success", services.findById(1L).getMessage());
	}
	
	@Test
	public void testFindById_SubjectNull() {
		when(repository.findSubjectById(Mockito.anyLong())).thenReturn(null);
		assertFalse(services.findById(1L).isResult());
	}
	
	@Test
	public void testFindById_Null() {
		assertFalse(services.findById(null).isResult());
	}
	
	@Test
	public void testFindById_Zero() {
		assertFalse(services.findById(0L).isResult());
	}
	
	@Test
	public void testFindGradeSubject() {
		List<Subject> subjectList= new ArrayList<>();
		subjectList.add(new Subject(1L, "name", "nameTeacher", 1L));	
		when(repository.findSubjectByGradeSubject(Mockito.anyLong())).thenReturn(subjectList);
		assertEquals("success", services.findGradeSubject(1L).getMessage());
	}
	
	@Test
	public void testFindGradeSubject_SubjectNull() {
		List<Subject> subjectList= new ArrayList<>();
		when(repository.findSubjectByGradeSubject(Mockito.anyLong())).thenReturn(subjectList);
		assertFalse(services.findGradeSubject(1L).isResult());
	}
	
	@Test
	public void testFindGradeSubject_Null() {
		assertFalse(services.findGradeSubject(null).isResult());
	}
	
	@Test
	public void testFindGradeSubject_Zero() {
		assertFalse(services.findGradeSubject(0L).isResult());
	}
	
	@Test
	public void testUpdate() {
		Subject subject = new Subject(1L, "name", "nameTeacher", 1L);		
		when(repository.findSubjectById(Mockito.anyLong())).thenReturn(subject);
		assertEquals("success", services.update(subject,1L).getMessage());
	}
	
	@Test
	public void testUpdate_SubjectNull() {
		Subject subject = new Subject(1L, "name", "nameTeacher", 1L);	
		when(repository.findSubjectById(Mockito.anyLong())).thenReturn(null);
		assertFalse(services.update(subject,1L).isResult());
	}
	
	@Test
	public void testUpdate_Null() {
		Subject subject = new Subject(1L, "name", "nameTeacher", 1L);	
		assertFalse(services.update(subject,null).isResult());
	}
	
	@Test
	public void testUpdate_Zero() {
		Subject subject = new Subject(1L, "name", "nameTeacher", 1L);	
		assertFalse(services.update(subject,0L).isResult());
	}
	
	@Test
	public void testDeleteById() {
		Subject subject = new Subject(1L, "name", "nameTeacher", 1L);		
		when(repository.findSubjectById(Mockito.anyLong())).thenReturn(subject);
		assertEquals("success", services.deleteById(1L).getMessage());
	}
	
	@Test
	public void testDeleteById_SubjectNull() {	
		when(repository.findSubjectById(Mockito.anyLong())).thenReturn(null);
		assertFalse(services.deleteById(1L).isResult());
	}
	
	@Test
	public void testDeleteById_Null() {
		assertFalse(services.deleteById(null).isResult());
	}
	
	@Test
	public void testDeleteById_Zero() {	
		assertFalse(services.deleteById(0L).isResult());
	}
}
