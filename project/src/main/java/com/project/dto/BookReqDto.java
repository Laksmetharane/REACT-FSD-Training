package com.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookReqDto(
        @NotBlank(message="title field is mandatory")
        @NotNull
        String title,
        String summary
) {
}
