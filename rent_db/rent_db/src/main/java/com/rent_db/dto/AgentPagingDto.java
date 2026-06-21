package com.rent_db.dto;

import java.util.List;

public record AgentPagingDto(
        long totalRecords,
        int totalPages,
        List<AgentRespDto> data
) {
}
