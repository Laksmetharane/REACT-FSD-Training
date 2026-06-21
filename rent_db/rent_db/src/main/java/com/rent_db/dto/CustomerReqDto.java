package com.rent_db.dto;

import com.rent_db.enums.Role;
import com.rent_db.model.Admin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerReqDto(
        @NotBlank(message="name field is mandatory")
        @NotNull
        String name,
        @NotBlank(message="username is mandatory")
        @NotNull
        String user_name,
        String password,
        @NotBlank(message="mobile number is mandatory")
        @NotNull
        String mob_no,
        @NotBlank(message="email is mandatory")
        @NotNull
        String email,
        @NotBlank(message="driving license is mandatory")
        @NotNull
        int dl_no,
        String id_path,
        int admin_id
) {
}
