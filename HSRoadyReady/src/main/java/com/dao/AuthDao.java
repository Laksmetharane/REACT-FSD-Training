package com.dao;

import com.model.User;
import org.springframework.stereotype.Component;

@Component
public interface AuthDao {
    User login(String username, String password);
}
