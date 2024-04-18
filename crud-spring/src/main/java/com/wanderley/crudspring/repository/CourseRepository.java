package com.wanderley.crudspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wanderley.crudspring.model.Course;

@Repository                                          //objeto, tipo da chave (id)
public interface CourseRepository extends JpaRepository<Course, Long> {

    
}
