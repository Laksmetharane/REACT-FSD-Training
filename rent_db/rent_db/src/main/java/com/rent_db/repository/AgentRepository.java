package com.rent_db.repository;

import com.rent_db.model.RentalAgent;
import org.aspectj.weaver.loadtime.Agent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgentRepository extends JpaRepository<RentalAgent,Integer> {

    RentalAgent findByUserUsername(String username);


    @Query("""
            select a from RentalAgent a where a.isActive=?1
            """)
    Page<RentalAgent> getAllActive(boolean b, Pageable pageable);
}
