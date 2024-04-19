package com.wanderley.crudspring.controller;

import java.util.List;

//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanderley.crudspring.model.Course;
import com.wanderley.crudspring.repository.CourseRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class CoursesController {

    private CourseRepository courseRepository;

    // public CoursesController(CourseRepository courseRepository) {
    //     this.courseRepository = courseRepository;
    // }

    @GetMapping
    public List<Course> list() {
        return this.courseRepository.findAll();
    }

}
