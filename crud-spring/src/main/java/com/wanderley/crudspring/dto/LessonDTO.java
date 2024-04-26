package com.wanderley.crudspring.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LessonDTO(
    Long id,
    @NotBlank @NotNull @Length(min = 3, max = 100) String name,
    @NotBlank @NotNull @Length(min = 3, max = 30) String youtubeUrl
) {
    
}
