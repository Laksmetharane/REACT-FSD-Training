package com.project.repository;

import com.project.model.Seeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeekerRepository extends JpaRepository<Seeker,Integer> {

    Seeker findByUserUsername(String sUsername);
}
