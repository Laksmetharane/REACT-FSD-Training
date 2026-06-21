package com.rent_db.dto;

public record ChangePasswordDto(
        String oldPassword,
        String newPassword
) {
}
