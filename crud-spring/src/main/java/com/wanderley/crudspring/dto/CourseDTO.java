package com.wanderley.crudspring.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
        @JsonProperty("_id") long id,

        @NotBlank @NotNull @Length(min = 3, max = 100) String name,

        @NotNull @Length(max = 30) String category) {
}
