package com.wanderley.crudspring.dto.mapper;

import org.springframework.stereotype.Component;

import com.wanderley.crudspring.dto.CourseDTO;
import com.wanderley.crudspring.enums.Category;
import com.wanderley.crudspring.model.Course;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course){
        if(course == null){
            return null;
        }
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue());
    }

    public Course toEntity(CourseDTO courseDTO){
        if(courseDTO == null){
            return null;
        }

        Course course = new Course();
        if(courseDTO.id() > 0){
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(convertCategoryValue(courseDTO.category()));
        return course;
    }

    public Category convertCategoryValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Front-end" -> Category.FRONT_END;
            case "Back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Invalid Category.");
        };
    }
    
}
