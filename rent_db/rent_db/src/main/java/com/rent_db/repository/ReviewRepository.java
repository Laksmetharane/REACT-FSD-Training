package com.rent_db.repository;

import com.rent_db.dto.ReviewRespDto;
import com.rent_db.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findByRating(int rating);

    List<Review> findByCarId(int id);

    @Query("""
            select r from Review r where r.customer.user.username=?1
            """)

    List<Review> getByCustomer(String username);


}
