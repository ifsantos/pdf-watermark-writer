package com.github.ifsantos.courses.repository;

import com.github.ifsantos.courses.api.model.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    
}
