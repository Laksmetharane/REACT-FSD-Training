package com.rent_db.dto;

import com.rent_db.enums.Availability;
import jakarta.validation.constraints.NotNull;

public record CarDto(
    @NotNull(message = "This field is mandatory")
    String model,

    @NotNull(message = "this field cannot be null")
    Availability availability,

    @NotNull(message = "Not null")
     int price,

    @NotNull
     int no_of_seats

    ){


}
