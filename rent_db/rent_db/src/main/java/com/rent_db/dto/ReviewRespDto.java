package com.rent_db.dto;

public record ReviewRespDto(
        int review_id,
        String comment,
        int rating,
        int car_id,
        int customer_id,
        String name
) {
}
