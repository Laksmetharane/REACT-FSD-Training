package com.model;

import com.enums.Reservation_Status;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private String location;

    @Column(nullable=false)
    private LocalDate p_date;

    @Column(nullable=false)
    private LocalDate d_date;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Admin admin;

    @Enumerated(EnumType.STRING)
    private Reservation_Status reservation_status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation(String s) {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getP_date(LocalDate parse) {
        return p_date;
    }

    public void setP_date(LocalDate p_date) {
        this.p_date = p_date;
    }

    public LocalDate getD_date(LocalDate parse) {
        return d_date;
    }

    public void setD_date(LocalDate d_date) {
        this.d_date = d_date;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Reservation_Status getReservation_status() {
        return reservation_status;
    }

    public void setReservation_status(Reservation_Status reservation_status) {
        this.reservation_status = reservation_status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", p_date=" + p_date +
                ", d_date=" + d_date +
                ", car_id=" + car.getCar_id() +
                ", reservation_status=" + reservation_status +
                '}';
    }
}
