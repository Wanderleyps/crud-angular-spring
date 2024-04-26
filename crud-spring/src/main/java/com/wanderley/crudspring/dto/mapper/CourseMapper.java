package com.wanderley.crudspring.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.wanderley.crudspring.dto.CourseDTO;
import com.wanderley.crudspring.dto.LessonDTO;
import com.wanderley.crudspring.enums.Category;
import com.wanderley.crudspring.model.Course;
import com.wanderley.crudspring.model.Lesson;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course){
        if(course == null){
            return null;
        }
        List<LessonDTO> lessonDTOList = course.getLessons()
                .stream()
                .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
                .toList();
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(),
                lessonDTOList);
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

         List<Lesson> lessons = courseDTO.lessons().stream()
                .map(lessonDTO -> {
                    Lesson lesson = new Lesson();
                    if (lesson.getId() > 0) {
                        lesson.setId(lessonDTO.id());
                    }
                    lesson.setName(lessonDTO.name());
                    lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
                    lesson.setCourse(course);
                    return lesson;
                }).collect(Collectors.toList());
        course.setLessons(lessons);

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
