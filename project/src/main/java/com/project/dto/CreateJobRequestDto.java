package com.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateJobRequestDto(
        @NotBlank (message="Title is mandatory")
        String title,
        @NotBlank(message="description is mandatory")
        String description,
        String location,
        @NotNull
        int salary

) {
}
