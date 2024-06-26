package com.wanderley.crudspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wanderley.crudspring.dto.CourseDTO;
import com.wanderley.crudspring.dto.CoursePageDTO;
import com.wanderley.crudspring.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private CourseService courseService;

    public CoursesController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public CoursePageDTO findAll(@RequestParam(defaultValue = "0") @PositiveOrZero int page, 
                                 @RequestParam(defaultValue = "10") @Positive @Max(1000) int pageSize) {
        return this.courseService.findAll(page, pageSize);
    }

    @GetMapping("/{id}")
    public CourseDTO findById(@PathVariable @NotNull @Positive long id) {
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CourseDTO create(@RequestBody @Valid CourseDTO course) {
        return courseService.create(course);
    }

    @PutMapping(value = "/{id}")
    public CourseDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid CourseDTO course) {
        return courseService.update(id, course);
    }
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
       courseService.delete(id);
    }

}
