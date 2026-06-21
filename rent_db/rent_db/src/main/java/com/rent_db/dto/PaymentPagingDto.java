package com.rent_db.dto;

import java.util.List;

public record PaymentPagingDto(
        long totalRecords,
        int totalPages,
        List<PaymentRespDto> data
) {
}
