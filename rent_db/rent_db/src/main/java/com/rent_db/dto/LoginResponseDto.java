package com.rent_db.dto;

public record LoginResponseDto(
        int id,
        String username,
        String role
) {
}
