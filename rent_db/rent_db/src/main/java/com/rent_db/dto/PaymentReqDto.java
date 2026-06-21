package com.rent_db.dto;

import com.rent_db.enums.PaymentMethod;

public record PaymentReqDto(
        int reservationId,
        PaymentMethod paymentMethod
) {
}
