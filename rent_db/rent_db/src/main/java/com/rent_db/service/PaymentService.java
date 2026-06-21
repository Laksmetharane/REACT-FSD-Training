package com.rent_db.service;

import com.rent_db.dto.PaymentPagingDto;
import com.rent_db.dto.PaymentReqDto;
import com.rent_db.dto.PaymentRespDto;
import com.rent_db.enums.Availability;
import com.rent_db.enums.PaymentStatus;
import com.rent_db.enums.Reservation_Status;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.mapper.PaymentMapper;
import com.rent_db.model.Car;
import com.rent_db.model.Payment;
import com.rent_db.model.Reservation;
import com.rent_db.repository.CarRepository;
import com.rent_db.repository.PaymentRepository;
import com.rent_db.repository.ReserveRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class PaymentService {
    private final ReserveRepository reserveRepository;
    private final PaymentRepository paymentRepository;
    private final CarRepository carRepository;
    private final PaymentMapper paymentMapper;

    public void  makePayment(PaymentReqDto dto) {
        Reservation reservation = reserveRepository.findById(dto.reservationId())
                .orElseThrow(()-> new ResourceNotFoundException("Invalid id"));
        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setAmount(reservation.getTotalAmount());
        payment.setPaymentMethod(dto.paymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(payment);
    }


    public PaymentRespDto approve(int id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid id given"));
        payment.setStatus(PaymentStatus.APPROVED);
        Reservation reservation = payment.getReservation();
        reservation.setReservation_status(Reservation_Status.PAID);
        Car car = reservation.getCar();
        car.setAvailability(Availability.NO);
        paymentRepository.save(payment);
        reserveRepository.save(reservation);
        carRepository.save(car);
        return paymentMapper.mapToDto(payment);
    }


    public PaymentRespDto reject(int paymentId){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()->new ResourceNotFoundException("Invalid id given"));
        payment.setStatus(PaymentStatus.REJECTED);
        paymentRepository.save(payment);
        return paymentMapper.mapToDto(payment);
    }


    public PaymentPagingDto getAll(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Payment> pages = paymentRepository.findAll(pageable);
        List<PaymentRespDto>list = pages.stream().map(paymentMapper::mapToDto).toList();
        return paymentMapper.MapToDto(list,pages);
    }


    public PaymentRespDto getById(int id){
        Payment payment = paymentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid id"));
        return paymentMapper.mapToDto(payment);
    }
}
