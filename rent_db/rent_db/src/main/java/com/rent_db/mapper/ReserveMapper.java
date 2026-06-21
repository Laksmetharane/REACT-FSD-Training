package com.rent_db.mapper;

import com.rent_db.dto.*;
import com.rent_db.enums.Reservation_Status;
import com.rent_db.model.Car;
import com.rent_db.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ReserveMapper {

    public static Reservation mapDtoToEntity(ReserveReqDto dto) {
        Reservation reservation = new Reservation();
        reservation.setP_date(dto.p_date());
        reservation.setD_date(dto.d_date());
        reservation.setLocation(dto.location());
        reservation.setReservation_status(Reservation_Status.WAITING);
        return reservation;
    }

    public static Reservation mapExistingToUpdated(Reservation existing, ReserveReqDto dto,Car car) {
        existing.setP_date(dto.p_date());
        existing.setD_date(dto.d_date());
        existing.setLocation(dto.location());
        long days = ChronoUnit.DAYS.between(
                dto.p_date(),dto.d_date()
        );
        double amount = days*car.getPrice();
        existing.setTotalAmount(amount);
        return existing;
    }

    public static ReservePagingDto MapEntityToDto(List<ReserveAllRespDto> list, Page<Reservation> pages) {
        return new ReservePagingDto(
                pages.getTotalElements(),
                pages.getTotalPages(),
                list
        );
    }

    public static ReserveAllRespDto mapEntityToDto(Reservation reservation,int agent_id) {
       return new ReserveAllRespDto(
        reservation.getId(),
        reservation.getLocation(),
        reservation.getReservation_status(),
        reservation.getP_date(),
        reservation.getD_date(),
        reservation.getCar().getId(),
        reservation.getCar().getAvailability(),
        reservation.getCar().getPrice(),
        reservation.getCar().getNo_of_seats(),
        reservation.getCustomer().getId(),
        reservation.getCustomer().getName(),
        reservation.getCustomer().getUser().getUsername(), reservation.getCustomer().getUser().getRole(),
        reservation.getTotalAmount(),
        reservation.getExtraAmount(),
        agent_id, reservation.isPaymentRequested());
    }
    public static ReserveAllRespDto mapEntityTodto(Reservation reservation) {
        return new ReserveAllRespDto(
                reservation.getId(),
                reservation.getLocation(),
                reservation.getReservation_status(),
                reservation.getP_date(),
                reservation.getD_date(),
                reservation.getCar().getId(),
                reservation.getCar().getAvailability(),
                reservation.getCar().getPrice(),
                reservation.getCar().getNo_of_seats(),
                reservation.getCustomer().getId(),
                reservation.getCustomer().getName(),
                reservation.getCustomer().getUser().getUsername(), reservation.getCustomer().getUser().getRole(),
                reservation.getTotalAmount(),
                reservation.getExtraAmount(),
                reservation.getRentalAgent().getId(),
                reservation.isPaymentRequested());
    }

    public static Reservation Mapmilleage(Reservation reservation,milleage_fuelDto dto) {
        reservation.setReturnMileage(dto.returnMileage());
        reservation.setFuelReturnedFull(dto.fuelReturnedFull());
        return reservation;
    }

    public Reservation mapDtoToEntity(ReserveAllRespDto dto){
        Reservation reservation = new Reservation();
        reservation.setId(dto.Reserve_id());
        reservation.setLocation(dto.location());
        reservation.setReservation_status(dto.reservation_status());
        reservation.setP_date(dto.p_date());
        reservation.setD_date(dto.d_date());
        reservation.getCar().setId(dto.car_id());
        reservation.getCar().setAvailability(dto.availability());
        reservation.getCar().setPrice(dto.price());
        reservation.getCar().setNo_of_seats(dto.no_of_seats());
        reservation.getCustomer().setId(dto.custom_id());
        reservation.getCustomer().setName(dto.custom_name());
        reservation.getCustomer().getUser().setUsername(dto.user_name());
        reservation.getCustomer().getUser().setRole(dto.role());

        return reservation;
    }
    public Reservation mapDtoToEntity(ReserveRespDto dto){
        Reservation reservation = new Reservation();
        reservation.setLocation(dto.location());
        reservation.setP_date(dto.p_date());
        reservation.setD_date(dto.d_date());
        reservation.setReservation_status(dto.reservation_status());
        return reservation;
    }

    public static ReserveRespDto mapRespEntityToDto(Reservation reservation) {
        return new ReserveRespDto(
                reservation.getLocation(),
                reservation.getReservation_status(),
                reservation.getP_date(),
                reservation.getD_date()
        );
    }
}
