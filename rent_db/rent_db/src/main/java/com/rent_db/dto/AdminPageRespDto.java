package com.rent_db.dto;

import com.rent_db.model.Admin;

import java.util.List;

public record AdminPageRespDto(
        long totalRecords,
        int totalPages,
        List<AdminRespDto> data
) {
}
