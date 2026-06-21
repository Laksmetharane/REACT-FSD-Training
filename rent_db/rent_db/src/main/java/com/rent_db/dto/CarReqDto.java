package com.rent_db.dto;

import com.rent_db.enums.Availability;
import jakarta.validation.constraints.NotNull;

public record CarReqDto(
        @NotNull(message = "This field is mandatory")
        String model,

        Availability availability,

        @NotNull(message = "Not null")
        int price,

        @NotNull
        int no_of_seats,

        @NotNull
        int pick_up_mileage,

        int admin_id,

        int agent_id,

        String idPath
) {
}
