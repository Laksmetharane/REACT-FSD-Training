package com.rent_db.repository;

import com.rent_db.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Integer> {
    //findall,getById,deleteById,save,saveAll
}
