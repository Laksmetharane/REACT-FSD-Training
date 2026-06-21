package com.rent_db.repository;

import com.rent_db.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Admin findByUserUsername(String adminUsername);
}
