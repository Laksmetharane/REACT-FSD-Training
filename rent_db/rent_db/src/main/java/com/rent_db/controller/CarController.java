package com.rent_db.controller;


import com.rent_db.dto.CarDto;
import com.rent_db.dto.CarPagingDto;
import com.rent_db.dto.CarReqDto;
import com.rent_db.dto.CarwithoutPagingDto;
import com.rent_db.enums.Availability;
import com.rent_db.model.Car;
import com.rent_db.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/car")
@CrossOrigin(origins="http://localhost:5173")
public class CarController {
    private final CarService carService;
    //to get all cars
    @GetMapping("/all")
    public List<Car> getAll(){

        return carService.getAll();
    }

    //to get all cars with pagination
    @GetMapping("/paginated/car")
    public CarwithoutPagingDto getAllV2(@RequestParam int page, @RequestParam  int size, @RequestParam(required=false)String sortBy, @RequestParam(required=false)String direction){
        return carService.getAllWithPagination(page,size,sortBy,direction);
    }

    //get using Id
    @GetMapping("/get-one/{id}")
    public ResponseEntity<CarDto> getById(@PathVariable int id){
        return ResponseEntity.ok(carService.getById(id));
    }

    public Car getCarById(int id) {
        return carService.getCarById(id);

    }

    @GetMapping("/search")
    public CarPagingDto SearchCarsByModel(@RequestParam String model,@RequestParam int page, @RequestParam  int size){
        return carService.SearchCarsByModel(model,page,size);
    }

    //get using Car Availability
    @GetMapping("/availability")
    public CarPagingDto getByAvailability(@RequestParam Availability availability,@RequestParam int page,@RequestParam int size){
        return carService.getByAvailability(availability,page,size);
    }

    @GetMapping("/byReservationId/{id}")
    public CarDto getCarByReservationId(@PathVariable int id){
        return carService.getCarByReservationId(id);
    }


    //to Add new car
    @PostMapping ("/add")
    public void addCar(@Valid @RequestBody CarReqDto dto, Principal principal){
        String AdminUsername = principal.getName();
        carService.addCar(dto,AdminUsername);
    }

    // to delete
    @PutMapping("/delete/{id}")
    public void deleteBy(@PathVariable int id){
            carService.deleteById(id);
    }


    //to update
    @PutMapping("/update/{id}")
    public void update(@PathVariable int id,@RequestBody Car updatedCar){
        carService.update(id,updatedCar);

    }

    @DeleteMapping("/soft-delete/{id}")
    public void softDelete(@PathVariable int id){
        carService.softDelete(id);
    }

    @PostMapping("/image/upload")
    public String upload( @RequestParam("file") MultipartFile file) throws IOException {
        return carService.upload(file);
    }






}
