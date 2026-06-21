package com.rent_db.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AgentDto(
        @NotBlank
        @NotNull
        @Size(min=4,message="Name should be at least 4 characters")
        String username,
        @NotBlank
        @NotNull
        @Size(min=4,message="Username should be at-least 4 characters")
        String name,
        String mob_no,
        String address
) {
}
