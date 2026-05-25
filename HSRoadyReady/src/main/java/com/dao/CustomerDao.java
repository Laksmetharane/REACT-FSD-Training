package com.dao;

import com.model.Customer;
import org.springframework.stereotype.Component;

@Component
public interface CustomerDao {
    Customer getByUsername(String customerUsername);
}
