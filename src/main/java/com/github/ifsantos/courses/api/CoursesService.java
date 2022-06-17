package com.github.ifsantos.courses.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.ifsantos.courses.api.model.Course;
import com.github.ifsantos.courses.repository.CourseRepository;

import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CoursesService {
    
    CourseRepository repo;

    @GetMapping
    public List<Course> list(){
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Course save(@RequestBody Course course){
        return repo.save(course);
    }

    @PutMapping
    public ResponseEntity<Course> update(@RequestBody Course course){
        if (course.getId() == null)
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        
        Optional<Course> findById = repo.findById(course.getId());
        
        if (findById.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        Course stored = findById.get();
        stored.setCategory(course.getCategory());
        stored.setName(course.getName());
        return ResponseEntity.status(HttpStatus.OK).body(repo.save(stored));
        
    }
    @DeleteMapping
    public ResponseEntity<Course> delete(@RequestBody Course course){
        if (course.getId() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        
        Optional<Course> findById = repo.findById(course.getId());
        
        if (findById.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        Course stored = findById.get();
        repo.delete(stored);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }

}
