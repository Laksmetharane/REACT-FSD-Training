package com.rent_db.dto;

import com.rent_db.model.Car;

import java.util.List;

public record CarRespDto(
        long totalRecords, int totalPages, List<Car> data
){
}
