package com.dao;


import com.exceptions.ResourceNotFoundException;
import com.model.Car;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public interface CarDao {
    //static void insert(Car car);

    void insert(Car car);

    List<Car> getAll();
    Car getById(int id)throws ResourceNotFoundException;
    void deleteById(int id)throws ResourceNotFoundException;
    void update(Car car);

}
