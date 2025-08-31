package com.batch.processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batch.processing.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
