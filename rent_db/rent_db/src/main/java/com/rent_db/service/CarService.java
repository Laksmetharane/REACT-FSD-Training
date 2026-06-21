package com.rent_db.service;

import com.rent_db.dto.*;
import com.rent_db.enums.Availability;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.mapper.CarMapper;
import com.rent_db.mapper.ReviewMapper;
import com.rent_db.model.Admin;
import com.rent_db.model.Car;
import com.rent_db.model.RentalAgent;
import com.rent_db.model.Review;
import com.rent_db.repository.AgentRepository;
import com.rent_db.repository.CarRepository;
import com.rent_db.repository.ReviewRepository;
import com.rent_db.util.FileUtility;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@AllArgsConstructor
public class CarService {
    private CarRepository carRepository;
    private final CarMapper carMapper;
    private final AdminService adminService;
    private final AgentService agentService;
    private final AgentRepository agentRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private static final String UPLOAD_LOC = "C:/Users/ELCOT/MyCarApp/public/images";
    public List<Car> getAll() {

        List<Car>list = carRepository.findAll();
        return list;
    }

    public Car addCar(CarReqDto dto, String adminUsername) {
        Car car = CarMapper.mapDtoEntity(dto);
        Admin admin = adminService.getByUsername(adminUsername);
        car.setAdmin(admin);
        int id = dto.agent_id();
        RentalAgent agent = agentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid id"));
        car.setAgent(agent);
        carRepository.save(car);
        return car;
    }

    public CarDto getById(int id) {
        Car car = carRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Invalid id given"));
        return carMapper.mapEntityDto(car);
    }

    public void deleteById(int id){
        getCarById(id);
        carRepository.deleteById(id);
    }

    public void update(int id, Car updatedCar) {
        Car existingCar = getCarById(id);
        existingCar.setModel(updatedCar.getModel());
        existingCar.setAvailability(updatedCar.getAvailability());
        existingCar.setPrice(updatedCar.getPrice());
        existingCar.setNo_of_seats(updatedCar.getNo_of_seats());
        existingCar.setPick_up_mileage(updatedCar.getPick_up_mileage());
        existingCar.setIdPath(updatedCar.getIdPath());
        carRepository.save(existingCar);
    }

    public CarwithoutPagingDto getAllWithPagination(int page, int size,String sortBy,String direction) {
        Pageable pageable;
        if(sortBy!=null && direction!=null){
            pageable = PageRequest.of(page,size,Sort.by(direction.equalsIgnoreCase("desc")?Sort.Direction.DESC: Sort.Direction.ASC,sortBy));
        }
        else{
            pageable = PageRequest.of(page,size);
        }
        Page<Car> pages = carRepository.getAllActive(true,pageable);
        List<CarwithoutDto>list = pages.stream().map(car-> {
            List<ReviewRespDto> reviews = reviewRepository.findByCarId(car.getId()).stream().map(reviewMapper::mapEntityToDto).toList();
            return carMapper.mapentityDto(car, reviews);
        }).toList();

        return carMapper.mapEntityTopagesDto(list, pages);
    }

    public CarPagingDto getByAvailability(Availability availability,int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Car> pages = carRepository.findByAvailability(availability,pageable);
        List<CarDto>list = pages.stream().map(carMapper::mapEntityDto).toList();
        return carMapper.mapEntityToPagesDto(list,pages);
    }

    public CarDto getCarByReservationId(int id) {
        Car car =carRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid id given"));
        return carMapper.mapEntityDto(car);
    }

    public Car getCarById(int id) {
        return carRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Invalid id given"));
    }

    public void softDelete(int id) {
        Car car = carRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Invalid id"));
        car.setActive(false);
        carRepository.save(car);
    }

    public CarPagingDto SearchCarsByModel(String model, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Car> pages = carRepository.findByModelContaining(model,pageable);
        List<CarDto>list = pages.stream().map(carMapper::mapEntityDto).toList();
        return carMapper.mapEntityToPagesDto(list,pages);
    }

    public String upload(MultipartFile file) throws IOException {
            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_LOC);
            Path destinationPath  = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(),destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }
    }
