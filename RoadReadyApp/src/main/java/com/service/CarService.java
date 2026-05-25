package com.service;

import com.model.Car;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarService {
private final Session session;
public CarService(Session session){
    this.session = session;
}
public List<Car> viewAllCars(){
    Transaction tx = session.beginTransaction();
    List<Car>list = session.createQuery("from Car",Car.class).list();
    tx.commit();
    return list;
}


















}
