package com.rent_db.mapper;

import com.rent_db.dto.*;
import com.rent_db.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarMapper {
    public static Car mapDtoEntity(CarReqDto dto){
        Car car = new Car();

        car.setModel(dto.model());
        car.setAvailability(dto.availability());
        car.setPrice(dto.price());
        car.setPick_up_mileage(dto.pick_up_mileage());
        car.setNo_of_seats(dto.no_of_seats());
        car.setIdPath(dto.idPath());
        return car;
    }

    public CarDto mapEntityDto(Car car) {
        return new CarDto(
                car.getModel(),
                car.getId(),
                car.getAvailability(),
                car.getPrice(),
                car.getNo_of_seats(),
                car.getPick_up_mileage(),
                car.getAdmin().getId(),
                car.getAgent().getId(),
                car.getIdPath()
        );
    }

    public CarwithoutPagingDto mapEntityTopagesDto(List<CarwithoutDto> list, Page<Car> pages){
        return new CarwithoutPagingDto(
                pages.getTotalElements(),
                pages.getTotalPages(),
                list
        );
    }
    public CarPagingDto mapEntityToPagesDto(List<CarDto> list, Page<Car> pages){
        return new CarPagingDto(
                pages.getTotalElements(),
                pages.getTotalPages(),
                list
        );
    }


    public CarwithoutDto mapentityDto(Car car,List<ReviewRespDto>reviews) {
        return new CarwithoutDto(
                car.getModel(),
                car.getId(),
                car.getAvailability(),
                car.getPrice(),
                car.getNo_of_seats(),
                car.getPick_up_mileage(),
                car.getAdmin().getId(),
                car.getAgent().getId(),
                car.getIdPath(),
                reviews
        );
    }
}
