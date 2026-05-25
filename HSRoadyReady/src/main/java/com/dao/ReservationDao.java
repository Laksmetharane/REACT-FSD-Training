package com.dao;

import com.model.Reservation;

public interface ReservationDao {
    void save(Reservation reservation,String customerUsername,int car_id);
    Reservation getById(int id,String customerUsername);
    void delete(int id);
}
