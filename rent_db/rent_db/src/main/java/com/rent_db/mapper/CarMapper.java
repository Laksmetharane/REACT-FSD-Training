package com.rent_db.mapper;

import com.rent_db.dto.CarDto;
import com.rent_db.dto.CarRespDto;
import com.rent_db.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarMapper {
    public Car mapDtoEntity(CarDto dto){
        Car car = new Car();
        car.setModel(dto.model());
        car.setAvailability(dto.availability());
        car.setPrice(dto.price());
        car.setNo_of_seats(dto.no_of_seats());
        return car;
    }

    public CarRespDto mapEntityToDto(Page<Car> pages){
        long totalElements = pages.getTotalElements();
        int totalPages = pages.getTotalPages();
        List<Car> list = pages.getContent();
        CarRespDto dto = new CarRespDto(totalElements,totalPages,list);
        return dto;
    }
}
