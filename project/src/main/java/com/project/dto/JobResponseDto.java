package com.project.dto;

import java.util.List;

public record JobResponseDto(
        int totalPages,
        long totalElements,
        List<JobResponse> jobs
) {
}
