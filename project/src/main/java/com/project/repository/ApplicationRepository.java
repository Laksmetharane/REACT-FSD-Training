package com.project.repository;

import com.project.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationRepository extends JpaRepository<Application,Integer> {

    @Query("""
            select a from Application a where a.seeker.user.username=?1
            """)
    Page<Application> findAllBySeekerUserUsername(String username, Pageable pageable);
}
