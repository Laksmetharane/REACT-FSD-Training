package com.rent_db.service;

import com.rent_db.dto.*;
import com.rent_db.enums.Availability;
import com.rent_db.enums.Reservation_Status;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.mapper.AgentMapper;
import com.rent_db.mapper.ReserveMapper;
import com.rent_db.model.*;
import com.rent_db.repository.ReserveRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final ReserveMapper reserveMapper;
    private final CarService carService;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final UserService userService;
    private final AgentService agentService;

    public ReservePagingDto getAll(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Reservation>pages = reserveRepository.findAll(pageable);
        List<ReserveAllRespDto>list= pages.stream().map(ReserveMapper::mapEntityTodto).toList();
        return ReserveMapper.MapEntityToDto(list,pages);
    }

    public Reservation getById(int id) {
        Reservation res = reserveRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Invalid id given"));
        return res;
    }

    public ReservePagingDto getByStatus(String username,Reservation_Status reservationStatus,int page,int size) {
        RentalAgent rentalAgent = agentService.getByUsername(username);
        int agent_id = rentalAgent.getId();
        Pageable pageable = PageRequest.of(page,size);
        Page<Reservation>pages = reserveRepository.getByStatus(reservationStatus,agent_id,pageable);
        List<ReserveAllRespDto> list = pages.stream().map(ReserveMapper::mapEntityTodto).toList();
        return ReserveMapper.MapEntityToDto(list,pages);
    }


    public void addReservation(ReserveReqDto dto, String CustomerUsername) {
        Car car = carService.getCarById(dto.car_id());
        if(car.getAvailability().equals(Availability.YES)){
        Customer customer = customerService.getByUsername(CustomerUsername);
        Reservation reservation = ReserveMapper.mapDtoToEntity(dto);
        reservation.setCar(car);
        reservation.setCustomer(customer);
        long days = ChronoUnit.DAYS.between(
                dto.p_date(),dto.d_date()
        );
        double amount = days*car.getPrice();
        reservation.setTotalAmount(amount);
        RentalAgent agent = agentService.getById(dto.agent_id());
        reservation.setRentalAgent(agent);
        reserveRepository.save(reservation);}
        else{
            throw new ResourceNotFoundException("Sorry the Car is not available at the moment");
        }
    }

    public void updateReservation(int id, ReserveReqDto dto,String CustomerUsername) {
        Reservation existing = getById(id);
        Car car = carService.getCarById(dto.car_id());
        if(car.getAvailability().equals(Availability.YES)){
            Customer customer = customerService.getByUsername(CustomerUsername);
            Reservation reservation = ReserveMapper.mapExistingToUpdated(existing,dto,car);
            reservation.setCar(car);
            reservation.setCustomer(customer);
            reserveRepository.save(reservation);}
        else{
            throw new ResourceNotFoundException("Sorry the Car is not available at the moment");
        }
    }

    public void deleteReservation(int id) {
        Reservation reservation = reserveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reserveRepository.delete(reservation);
    }

//    public ReservePagingDto getReservationByCustomer(String customerUsername, int page, int size) {
//        Pageable pageable = PageRequest.of(page,size);
//        Page<Reservation>pages = reserveRepository.getReservationByCustomer(customerUsername,pageable);
//        List<ReserveAllRespDto>list = pages.stream().map(ReserveMapper::mapEntityToDto).toList();
//        return ReserveMapper.MapEntityToDto(list,pages);
//    }

    public List<ReserveAllRespDto> getReservationByCustomer(String customerUsername) {
        List<Reservation>list = reserveRepository.getReservationByCustomer(customerUsername);
        return list.stream().map(ReserveMapper:: mapEntityTodto).toList();
    }

    public ReservePagingDto getReservationByAgent(String Username, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Reservation>pages = reserveRepository.getReservationByAgent(Username,pageable);
        List<ReserveAllRespDto>list = pages.stream().map(ReserveMapper::mapEntityTodto).toList();
        return ReserveMapper.MapEntityToDto(list,pages);
    }


    public void assignReservationToAgent(AgentDto dto, int reservationId) {
        Reservation reservation = reserveRepository.findById(reservationId).orElseThrow(()->new ResourceNotFoundException("Invalid id given"));
        RentalAgent agent = agentService.getByUsername(dto.username());
        reservation.setRentalAgent(agent);
        reserveRepository.save(reservation);
    }

    public void addMileage_fuel_status(String username, milleage_fuelDto dto){
        Reservation reservation = reserveRepository.getReservationByAgent(username);
        reservation = ReserveMapper.Mapmilleage(reservation,dto);
        reservation.setReservation_status(Reservation_Status.CONFIRMED);
        reserveRepository.save(reservation);
    }


    public double calculateExtraAmount(int id,milleage_fuelDto dto) {
        Reservation reservation = reserveRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid id"));
        reservation.setReturnMileage(dto.returnMileage());
        reservation.setFuelReturnedFull(dto.fuelReturnedFull());
        double taxRate = 0.12;
        double fuelPenalty;
        if(reservation.isFuelReturnedFull()){
            fuelPenalty = 0;
        }
        else {
            fuelPenalty = 45;
        }
        long days = ChronoUnit.DAYS.between(
                reservation.getP_date(),reservation.getD_date()
        );
        double baseCost = days * reservation.getCar().getPrice();
        int allowedMiles = (int)days * reservation.getCar().getPick_up_mileage();
        int actualMilesDriven = reservation.getReturnMileage()-reservation.getCar().getPick_up_mileage();
        int extraMiles = Math.max(0,(actualMilesDriven-allowedMiles));

        double mileageCost = extraMiles * 0.25;
        double subTotal = baseCost + mileageCost;
        double taxAmount = subTotal * taxRate;
        double overAllAmount = subTotal + taxAmount + fuelPenalty;
        reservation.setExtraAmount(overAllAmount-reservation.getTotalAmount());
        reservation.setPaymentRequested(true);
        reserveRepository.save(reservation);
        return overAllAmount - reservation.getTotalAmount();
    }

    public void approveReservation(int resId) {
        Reservation reservation = getById(resId);
        reservation.setReservation_status(Reservation_Status.CONFIRMED);
        reservation.getCar().setAvailability(Availability.NO);
        reserveRepository.save(reservation);
    }

    public void returnCar(int id) {
        Reservation reservation = reserveRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid id"));
        reservation.setReservation_status(Reservation_Status.PAID);
        reservation.getCar().setAvailability(Availability.YES);
        if(reservation.getExtraAmount()==0) {
            reservation.setReservation_status(Reservation_Status.PAID);
        }
        reservation.setPaymentRequested(false);
        reserveRepository.save(reservation);
    }
}
