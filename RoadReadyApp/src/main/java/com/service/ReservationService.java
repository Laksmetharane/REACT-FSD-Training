package com.service;

import com.enums.Availability;
import com.enums.Reservation_Status;
import com.model.Customer;
import com.model.Reservation;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ReservationService {
    private final Session session;
    private final CustomerService customerService;
    public ReservationService(Session session){
        this.session=session;
        customerService = new CustomerService(session);
    }
    public void add(Reservation reservation, String username) {
        Customer customer = customerService.getByUsername(username);
        reservation.setCustomer(customer);
        reservation.setReservation_status(Reservation_Status.CONFIRMED);
        Transaction tx = session.beginTransaction();
        session.persist(reservation);
        tx.commit();
    }
    public void cancelReservation(int res_id){
        Transaction tx = session.beginTransaction();
        Reservation reservation = session.find(Reservation.class,res_id);
        reservation.getCar().setAvailability(Availability.YES);
        int row = session.createQuery("delete from Reservation where id=:id").setParameter("id",res_id).executeUpdate();
        tx.commit();
    }
}
