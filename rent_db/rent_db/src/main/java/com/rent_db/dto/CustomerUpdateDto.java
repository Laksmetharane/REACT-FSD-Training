package com.rent_db.dto;

public record CustomerUpdateDto(
        String name,
        String mob_no,
        String email,
        int dl_no,
        String docIdPath
) {
}
