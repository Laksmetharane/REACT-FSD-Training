package com.rent_db.service;

import com.rent_db.dto.DashBoardStatsDto;
import com.rent_db.model.Car;
import com.rent_db.model.Customer;
import com.rent_db.model.Reservation;
import com.rent_db.repository.CarRepository;
import com.rent_db.repository.CustomerRepository;
import com.rent_db.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashBoardService {
    private final CarRepository carRepository;
    private final ReserveRepository reserveRepository;
    private final CustomerRepository customerRepository;

    public DashBoardStatsDto getStats() {
        List<Car> listcar = carRepository.findAll();
        List<Reservation> listReserve = reserveRepository.findAll();
        List<Customer>listcustomer = customerRepository.findAll();

        List<String> label = List.of("Car","Reservation","Customer");
        List<Long>count = List.of((long)listcar.size(),(long)listReserve.size(),(long)listcustomer.size());

        return new DashBoardStatsDto(label,count);
    }
}
