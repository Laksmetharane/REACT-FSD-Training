package com.rent_db.service;


import com.rent_db.dto.CarDto;
import com.rent_db.dto.CarReqDto;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.mapper.CarMapper;
import com.rent_db.model.Admin;
import com.rent_db.model.Car;
import com.rent_db.model.RentalAgent;
import com.rent_db.repository.AdminRepository;
import com.rent_db.repository.AgentRepository;
import com.rent_db.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.rent_db.enums.Availability.YES;
import static io.jsonwebtoken.impl.security.EdwardsCurve.findById;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @Mock
    private AdminService adminService;

    @Mock
    private AgentRepository agentRepository;

    @InjectMocks
    private CarService carService;


    private Car car;
    private Car car1;
    private Car car2;
    private Admin admin;
    private RentalAgent agent;

    @BeforeEach
    public void sampleDate(){
        car = new Car();
        car.setId(1);
        car.setModel("Thar");
        car.setPrice(5000);
        car.setNo_of_seats(5);
        car.setPick_up_mileage(25000);

        car1 = new Car();
        car1.setId(2);
        car1.setModel("MG");
        car1.setPrice(6000);
        car1.setNo_of_seats(6);
        car1.setPick_up_mileage(25500);

        car2 = new Car();
        car2.setId(3);
        car2.setModel("Hyundaii");
        car2.setPrice(4000);
        car2.setNo_of_seats(6);
        car2.setPick_up_mileage(25000);


        admin = new Admin();
        admin.setId(1);

        agent = new RentalAgent();
        agent.setId(1);


    }

    @Test
    public void getAllCars_MustReturnSomething(){
        when(carRepository.findAll()).thenReturn(List.of(car,car1));
        List<Car> actualCall = carService.getAll();

        assertThat(actualCall).hasSize(2);

        assertThat(actualCall.getFirst().getModel()).isEqualToIgnoringCase("Thar");
        assertThat(actualCall.get(1).getModel()).isEqualToIgnoringCase("MG");
    }

    @Test
    public void getAllCars_ReturnsEmptyList(){
        when(carRepository.findAll()).thenReturn(List.of());

        List<Car> actualCall = carService.getAll();
        assertThat(actualCall).hasSize(0);
        assertThat(actualCall).isEmpty();
    }

    @Test
    void getById_carExists(){
        when(carRepository.findById(100)).thenReturn(Optional.of(car));
        when(carRepository.findById(200)).thenReturn(Optional.of(car1));

        assertThat(carService.getCarById(100).getId()).isEqualTo(1);
        assertThat(carService.getCarById(200).getId()).isEqualTo(2);

        assertThat(carService.getCarById(100).getModel()).isEqualTo("Thar");
        assertThat(carService.getCarById(200).getModel()).isEqualTo("MG");

        assertThat(carService.getCarById(100).getNo_of_seats()).isEqualTo(5);
        assertThat(carService.getCarById(200).getNo_of_seats()).isEqualTo(6);

    }

    @Test
    void getById_carDoesNotExist(){
        when(carRepository.findById(100)).thenReturn(Optional.empty());
        assertThatThrownBy(()->carService.getById(100)).isInstanceOf(ResourceNotFoundException.class).hasMessage("Invalid id given");

        verify (carRepository,times(1)).findById(100);
    }

    @Test
    void addCar_mustSaveAndReturnCategory(){
        when(adminService.getByUsername("admin")).thenReturn(admin);
        when(agentRepository.findById(anyInt())).thenReturn(Optional.of(agent));
        when(carRepository.save(any(Car.class))).thenReturn(car2);


        CarReqDto dto = new CarReqDto("Hyundaii",null,4000,6,25000,0,0,null);

        Car actualCar = carService.addCar(dto,"admin");
        assertThat(actualCar.getModel()).isEqualTo(car2.getModel());
        assertThat(actualCar.getNo_of_seats()).isEqualTo(car2.getNo_of_seats());
        assertThat(actualCar.getPick_up_mileage()).isEqualTo(car2.getPick_up_mileage());
        assertThat(actualCar.getPrice()).isEqualTo(car2.getPrice());
        verify(carRepository,times(1)).save(any(Car.class));

    }

    @Test
    void deleteCar_mustDeleteAndReturnNothing(){
        when(carRepository.findById(100)).thenReturn(Optional.of(car));

        doNothing().when(carRepository).deleteById(100);
        carService.deleteById(100);
        verify(carRepository,times(1)).deleteById(100);
        verify(carRepository,times(1)).findById(100);
    }






}
