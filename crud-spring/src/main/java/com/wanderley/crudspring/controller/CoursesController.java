package com.wanderley.crudspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wanderley.crudspring.model.Course;
import com.wanderley.crudspring.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private CourseService courseService;

    public CoursesController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> list() {
        return this.courseService.list();
    }

    @GetMapping("/{id}")
    public Course findById(@PathVariable @NotNull @Positive long id) {
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course) {
        return courseService.create(course);
    }

    @PutMapping(value = "/{id}")
    public Course update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course course) {
        return courseService.update(id, course);
    }
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
       courseService.delete(id);
    }

}
