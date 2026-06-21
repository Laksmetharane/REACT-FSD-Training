package com.rent_db.dto;

import java.util.List;

public record DashBoardStatsDto(
        List<String> label,
        List<Long> count
) {
}
