package com.dao_impl;

import com.Exception.ResourceNotFoundException;
import com.dao.ReservationDao;
import com.enums.Availability;
import com.enums.Reservation_Status;
import com.model.Car;
import com.model.Customer;
import com.model.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ReservationDaoImpl implements ReservationDao {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private CustomerDaoImpl customerDao;
    @Override
    public void save(Reservation reservation, String customerUsername,int car_id) {
        Car car = em.find(Car.class,car_id);
        if(car.getAvailability().equals(Availability.YES)) {
            Customer customer = customerDao.getByUsername(customerUsername);
            reservation.setCustomer(customer);
            reservation.setReservation_status(Reservation_Status.CONFIRMED);
            System.out.println("Reserved successfully");
            em.persist(reservation);
        }
        else{
            System.out.println("Sorry the Car is not available right now");
        }
    }

    @Override
    public Reservation getById(int id, String customerUsername) {
        Reservation reservation = em.find(Reservation.class,id);
        int i = 0;
        if(reservation.getId()==0) {
            throw new ResourceNotFoundException("invalid id given..");
        }
        return null;
    }

    @Override
    public void delete(int id) {
        Reservation reservation = em.find(Reservation.class,id);
        Car car = reservation.getCar();
        car.setAvailability(Availability.YES);
        em.remove(reservation);
    }
}
