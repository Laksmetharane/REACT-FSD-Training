package com.dao;

import com.model.Car;

import java.util.List;

public interface CarDao {
    List<Car> findAll();
}
