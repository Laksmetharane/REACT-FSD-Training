package com.rent_db.controller;


import com.rent_db.dto.CarDto;
import com.rent_db.dto.CarRespDto;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.model.Car;
import com.rent_db.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/api/car/all")
    public List<Car> getAll(){
        return carService.getAll();
    }

    @GetMapping("/api/car/all/v2")
    public CarRespDto getAllV2(@RequestParam int page,@RequestParam  int size){
        return carService.getAllWithPagination(page,size);
    }

    @PostMapping ("/api/car/add")
    public void addCar(@Valid @RequestBody CarDto dto){
        carService.addCar(dto);
    }

    @GetMapping("/api/car/get-one/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id){
            return ResponseEntity.ok(carService.getById(id));
    }

    @PutMapping("/api/car/delete/{id}")
    public void deleteBy(@PathVariable int id){
            carService.deleteById(id);

    }

    @PutMapping("/api/car/update/{id}")
    public void update(@PathVariable int id,@RequestBody Car updatedCar){
        carService.update(id,updatedCar);

    }

}
