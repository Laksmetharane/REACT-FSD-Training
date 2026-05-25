package com.model;

import com.enums.Availability;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int car_id;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    private Availability availability;

    @Column(nullable  = false)
    private int price;

    @Column(nullable = false)
    private int no_of_seats;

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNo_of_seats() {
        return no_of_seats;
    }

    public void setNo_of_seats(int no_of_seats) {
        this.no_of_seats = no_of_seats;
    }

    @Override
    public String toString() {
        return "Car{" +
                "car_id=" + car_id +
                ", model='" + model + '\'' +
                ", availability=" + availability +
                ", price=" + price +
                ", no_of_seats=" + no_of_seats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return availability == car.availability;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(availability);
    }
}
