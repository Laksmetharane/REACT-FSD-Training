package com.rent_db.dto;

import com.rent_db.enums.Availability;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CarwithoutDto(
        @NotNull(message = "This field is mandatory")
        String model,

        int id,

        @NotNull(message = "this field cannot be null")
        Availability availability,

        @NotNull(message = "Not null")
        int price,

        @NotNull
        int no_of_seats,

        @NotNull
        int pick_up_mileage,

        @NotNull
        int admin_id,

        @NotNull
        int agent_id,

        String idPath,
        List<ReviewRespDto> reviews
) {
}
