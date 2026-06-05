package com.project.repository;

import com.project.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer,Integer> {
    Employer findByUserUsername(String eUsername);
}
