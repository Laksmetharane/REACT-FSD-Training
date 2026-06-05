package com.project.repository;

import com.project.enums.Role;
import com.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByRole(Role role);

    User findByUsername(String username);

    //User findByUsername(String username);
}
