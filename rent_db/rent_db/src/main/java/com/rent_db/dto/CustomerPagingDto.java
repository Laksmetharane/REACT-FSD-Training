package com.rent_db.dto;

import java.util.List;

public record CustomerPagingDto(
        long totalRecords,
        int totalPages,
        List<CustomerRespDto> data
) {
}
