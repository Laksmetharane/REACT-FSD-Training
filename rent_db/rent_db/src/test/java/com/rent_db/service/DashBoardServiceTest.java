package com.rent_db.service;


import com.rent_db.dto.DashBoardStatsDto;
import com.rent_db.model.Car;
import com.rent_db.model.Customer;
import com.rent_db.model.Reservation;
import com.rent_db.repository.CarRepository;
import com.rent_db.repository.CustomerRepository;
import com.rent_db.repository.ReserveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DashBoardServiceTest {
    @Mock
    private CarRepository carRepository;

    @Mock
    private ReserveRepository reserveRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private DashBoardService dashBoardService;

    @Test
    void getStats_mustReturnDashboardStats(){
        List<Car> cars = List.of(new Car(),new Car());
        List<Reservation> res = List.of(new Reservation(),new Reservation());
        List<Customer> cus = List.of(new Customer(),new Customer());

        when(carRepository.findAll()).thenReturn(cars);
        when(reserveRepository.findAll()).thenReturn(res);
        when(customerRepository.findAll()).thenReturn(cus);

        DashBoardStatsDto result = dashBoardService.getStats();
        assertThat(result.label()).containsExactly("Car","Reservation","Customer");
        assertThat(result.count()).containsExactly(2L,2L,2L);

        verify(carRepository,times(1)).findAll();
        verify(reserveRepository,times(1)).findAll();
        verify(customerRepository,times(1)).findAll();
    }

    @Test
    void getStats_mustReturnEmptyStats(){
        when(carRepository.findAll()).thenReturn(List.of());
        when(reserveRepository.findAll()).thenReturn(List.of());
        when(customerRepository.findAll()).thenReturn(List.of());

        DashBoardStatsDto result = dashBoardService.getStats();

        assertThat(result.label()).containsExactly("Car","Reservation","Customer");
        assertThat(result.count()).containsExactly(0L,0L,0L);
    }
}
