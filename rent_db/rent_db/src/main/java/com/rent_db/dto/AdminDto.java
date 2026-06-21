package com.rent_db.dto;

public record AdminDto(
        String user_name,
        String password,
        String name,
        int age
) {
}
