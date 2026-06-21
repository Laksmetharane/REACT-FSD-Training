package com.rent_db.dto;

import com.rent_db.enums.Role;

public record UserRespDto(
        int id,
        String name,
        Role role
) {
}
