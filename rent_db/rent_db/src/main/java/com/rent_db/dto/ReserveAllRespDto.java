package com.rent_db.dto;

import com.rent_db.enums.Availability;
import com.rent_db.enums.Reservation_Status;
import com.rent_db.enums.Role;

import java.time.LocalDate;

public record ReserveAllRespDto(
        int Reserve_id,
        String location,
        Reservation_Status reservation_status,
        LocalDate p_date,
        LocalDate d_date,
        int car_id,
        Availability availability,
        int price,
        int no_of_seats,
        int custom_id,
        String custom_name,
        String user_name,
        Role role,
        double totalAmount,
        double extraAmount,
        int agent_id,
        boolean paymentRequested
) {

}
