package com.rent_db.repository;

import com.rent_db.enums.Role;
import com.rent_db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//import java.lang.ScopedValue;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    @Query("""
            select u from User u where u.role=:role
            """)
    List<User> findByRole(Role role);
}
