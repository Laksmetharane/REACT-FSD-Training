package com.project.dto;

public record JobResponse(
        int id,
        String title,
        String location,
        int salary,
        String companyName
) {
}
