package com.service;

import com.model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerService {
    private final Session session;
    public CustomerService(Session session){
        this.session = session;
    }
    public Customer getByUsername(String customerUsername){
        Transaction tx = session.beginTransaction();
        Customer customer = session.createQuery("select c from Customer c where c.user.user_name=:user_name",Customer.class)
                .setParameter("user_name",customerUsername).getSingleResult();
        tx.commit();
        return customer;
    }
}
