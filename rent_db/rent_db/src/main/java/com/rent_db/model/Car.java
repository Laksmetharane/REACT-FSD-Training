package com.rent_db.model;

import com.rent_db.enums.Availability;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private String model;

    @Enumerated(EnumType.STRING)
    private Availability availability;

    @Column(nullable=false)
    private int price;

    @Column(nullable=false)
    private int no_of_seats;



}
