package com.rent_db.dto;

import java.util.List;

public record CarwithoutPagingDto(
        long totalRecords,
        int totalPages,
        List<CarwithoutDto> data
) {
}
