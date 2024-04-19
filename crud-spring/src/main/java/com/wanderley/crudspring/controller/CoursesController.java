package com.wanderley.crudspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wanderley.crudspring.model.Course;
import com.wanderley.crudspring.repository.CourseRepository;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CoursesController {

    private CourseRepository courseRepository;

    @GetMapping
    public List<Course> list() {
        return this.courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable long id) {
        return courseRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody Course course) {
        return courseRepository.save(course);
    }
    

}
