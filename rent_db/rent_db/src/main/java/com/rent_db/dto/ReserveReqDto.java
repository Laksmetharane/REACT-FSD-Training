package com.rent_db.dto;

import com.rent_db.enums.Reservation_Status;

import java.time.LocalDate;

public record ReserveReqDto(
        LocalDate p_date,
        LocalDate d_date,
        String location,
        int car_id,
        int agent_id
) {
}
