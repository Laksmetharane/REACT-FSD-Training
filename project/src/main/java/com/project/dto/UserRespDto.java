package com.project.dto;

import com.project.enums.Role;

public record UserRespDto(
        int id,
        String name,
        Role role
) {
}
