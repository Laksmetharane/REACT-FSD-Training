package com.rent_db.model;

import com.rent_db.enums.Reservation_Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private String location;

    @Enumerated(EnumType.STRING)
    private Reservation_Status reservation_status;

    @Column(nullable=false)
    private LocalDate p_date;

    @Column(nullable=false)
    private LocalDate d_date;

    @Column(nullable=false)
    private Double totalAmount;

    private int returnMileage;

    private boolean fuelReturnedFull;

    private double extraAmount;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private RentalAgent rentalAgent;

    private boolean paymentRequested;

}
