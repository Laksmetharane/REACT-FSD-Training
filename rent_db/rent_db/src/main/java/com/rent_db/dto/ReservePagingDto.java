package com.rent_db.dto;

import java.util.List;

public record ReservePagingDto(
        long totalRecords,
        int totalPages,
        List<ReserveAllRespDto> data
) {
}
