package com.rent_db.dto;

public record ReviewReqDto(
    String comment,
    int rating,
    int car_id){
}
