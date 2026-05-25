package com.rent_db.service;

import com.rent_db.dto.CarDto;
import com.rent_db.dto.CarRespDto;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.mapper.CarMapper;
import com.rent_db.model.Car;
import com.rent_db.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {
    private CarRepository carRepository;
    private final CarMapper carMapper;
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public void addCar(CarDto dto) {
        Car car = carMapper.mapDtoEntity(dto);
        carRepository.save(car);
    }

    public Car getById(int id) {
        return carRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Invalid id given"));
    }

    public void deleteById(int id){
        getById(id);
        carRepository.deleteById(id);
    }

    public void update(int id, Car updatedCar) {
        Car existingCar = getById(id);
        existingCar.setModel(updatedCar.getModel());
        existingCar.setAvailability(updatedCar.getAvailability());
        existingCar.setPrice(updatedCar.getPrice());
        existingCar.setNo_of_seats(updatedCar.getNo_of_seats());
        carRepository.save(existingCar);
    }

    public CarRespDto getAllWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Car> pages = carRepository.findAll(pageable);
        return carMapper.mapEntityToDto(pages);
    }
}
