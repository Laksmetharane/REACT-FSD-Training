package com.rent_db.mapper;

import com.rent_db.dto.ReviewRespDto;
import com.rent_db.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewRespDto mapEntityToDto(Review review) {
        return new ReviewRespDto(
                review.getId(),
                review.getComment(),
                review.getRating(),
                review.getCar().getId(),
                review.getCustomer().getId(),
                review.getCustomer().getName()
        );
    }
}
