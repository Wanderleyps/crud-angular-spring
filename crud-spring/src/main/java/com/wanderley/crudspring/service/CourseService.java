package com.wanderley.crudspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.wanderley.crudspring.dto.CourseDTO;
import com.wanderley.crudspring.dto.CoursePageDTO;
import com.wanderley.crudspring.dto.mapper.CourseMapper;
import com.wanderley.crudspring.exception.RecordNotFoundException;
import com.wanderley.crudspring.model.Course;
import com.wanderley.crudspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@Service
public class CourseService {

    private CourseRepository courseRepository;
    private CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO findAll(@PositiveOrZero int page, @Positive @Max(1000) int pageSize) {
        Page<Course> coursePage = courseRepository.findAll(PageRequest.of(page, pageSize));
        List<CourseDTO> list = coursePage.getContent().stream()
                .map(course -> courseMapper.toDTO(course))
                .toList();
        return new CoursePageDTO(list, coursePage.getTotalElements(), coursePage.getTotalPages());
    }

    // public List<CourseDTO> list() {
    //     return this.courseRepository.findAll()
    //                                 .stream()
    //                                 .map(courseMapper::toDTO)
    //                                 // Alternativa usando expressão lambda para mapeamento
    //                                 //.map(course -> courseMapper.toDTO(course))
    //                                 .collect(Collectors.toList());
    // }

    public CourseDTO findById(@NotNull @Positive long id) {
        return this.courseRepository.findById(id)
                               .map(courseMapper::toDTO)
                               .orElseThrow(() -> new RecordNotFoundException(id));

    }

    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @NotNull @Valid CourseDTO courseDTO) {
        return courseRepository.findById(id)
                .map(courseFound -> {
                    Course course = courseMapper.toEntity(courseDTO);
                    courseFound.setName(courseDTO.name());
                    courseFound.setCategory(this.courseMapper.convertCategoryValue(courseDTO.category()));
                    courseFound.getLessons().clear();
                    course.getLessons().forEach(lesson -> courseFound.getLessons().add(lesson));
                    return courseMapper.toDTO(courseRepository.save(courseFound));                    
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        courseRepository.delete(courseRepository.findById(id)
                        .orElseThrow(() -> new RecordNotFoundException(id)));        
    }
    
}
