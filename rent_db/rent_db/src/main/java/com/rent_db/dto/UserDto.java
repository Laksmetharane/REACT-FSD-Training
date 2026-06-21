package com.rent_db.dto;

import com.rent_db.enums.Role;

public record UserDto(
        String username,
        String password,
        Role role
) {
}
