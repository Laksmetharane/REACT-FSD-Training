package com.rent_db.repository;

import com.rent_db.enums.Availability;
import com.rent_db.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Integer> {
    Page<Car> findByAvailability(Availability availability, Pageable pageable);

    @Query("""
           select c from Reservation r join r.car c 
                      where r.id =?1
            
           """)
    Car findCarByRegistrationId(int registrationId);


    @Query("""
select c from Car c where c.isActive = ?1

""")
    Page<Car> getAllActive(boolean b, Pageable pageable);

    Page<Car> findByModelContaining(String model, Pageable pageable);
}
