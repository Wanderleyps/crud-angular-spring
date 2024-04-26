package com.wanderley.crudspring.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanderley.crudspring.enums.Category;
import com.wanderley.crudspring.enums.validation.ValueOfEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
        @JsonProperty("_id") long id,

        @NotBlank @NotNull @Length(min = 3, max = 100) String name,

       // @NotNull @Length(max = 30) @ValueOfEnum(enumClass = Category.class)  String category,

        @NotNull @Length(max = 30) String category,
        
        @NotNull @NotEmpty @Valid List<LessonDTO> lessons) {
}
