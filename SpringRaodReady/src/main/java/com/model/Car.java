package com.model;

import com.enums.Availability;

public class Car {
    private int car_id;
    private String model;
    private Availability availability;
    private int price;
    private int no_of_seats;


    public Car() {
    }

    public Car(String model, Availability availability, int price, int no_of_seats) {
        this.model = model;
        this.availability = availability;
        this.price = price;
        this.no_of_seats = no_of_seats;
    }

    public int getId() {
        return car_id;
    }

    public void setId(int id) {
        this.car_id = id;
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
                "id=" + car_id +
                ", model='" + model + '\'' +
                ", availability=" + availability +
                ", price=" + price +
                ", no_of_seats=" + no_of_seats +
                '}';
    }

}

