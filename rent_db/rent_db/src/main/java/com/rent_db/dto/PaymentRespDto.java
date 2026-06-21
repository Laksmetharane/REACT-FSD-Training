package com.rent_db.dto;

import com.rent_db.enums.PaymentMethod;
import com.rent_db.enums.PaymentStatus;

public record PaymentRespDto(
        int id,
        double amount,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        int reservationId
) {
}
