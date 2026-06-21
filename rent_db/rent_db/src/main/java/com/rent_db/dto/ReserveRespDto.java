package com.rent_db.dto;

import com.rent_db.enums.Reservation_Status;

import java.time.LocalDate;

public record ReserveRespDto(
        String location,
        Reservation_Status reservation_status,
        LocalDate p_date,
        LocalDate d_date
        )
{
}
