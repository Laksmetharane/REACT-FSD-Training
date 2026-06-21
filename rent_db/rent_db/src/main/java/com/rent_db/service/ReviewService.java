package com.rent_db.service;

import com.rent_db.dto.ReviewReqDto;
import com.rent_db.dto.ReviewRespDto;
import com.rent_db.enums.Role;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.mapper.ReviewMapper;
import com.rent_db.model.Car;
import com.rent_db.model.Customer;
import com.rent_db.model.Review;
import com.rent_db.model.User;
import com.rent_db.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private final CustomerService customerService;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CarService carService;
    private final UserService userService;

    public List<ReviewRespDto> getAll() {
        List<Review>list = reviewRepository.findAll();
        return list.stream().map(reviewMapper::mapEntityToDto).toList();
    }

    public ReviewRespDto getById(int id) {
        Review review = reviewRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Id not valid"));
        return reviewMapper.mapEntityToDto(review);
    }

    public List<ReviewRespDto> getByRating(int rating) {
        List<Review> list = reviewRepository.findByRating(rating);
        return list.stream().map(reviewMapper::mapEntityToDto).toList();
    }


    public List<ReviewRespDto> getByCarId(int id) {
        List<Review>list = reviewRepository.findByCarId(id);
        return list.stream().map(reviewMapper::mapEntityToDto).toList();
    }


    public List<ReviewRespDto> getByCustomer(String username) {
        List<Review> list = reviewRepository.getByCustomer(username);
        return list.stream().map(reviewMapper::mapEntityToDto).toList();


    }

    public void addReview(ReviewReqDto reviewReqDto, String username) {
        Customer customer = customerService.getByUsername(username);
        Review review = new Review();
        review.setComment(reviewReqDto.comment());
        review.setRating(reviewReqDto.rating());
        Car car = carService.getCarById(reviewReqDto.car_id());
        review.setCar(car);
        review.setCustomer(customer);
        reviewRepository.save(review);
    }

    public void updateReview(int id, ReviewReqDto reviewReqDto, String username) {
        User user = userService.getByUsername(username);

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Only admin can update reviews");
        }

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRating(reviewReqDto.rating());
        review.setComment(reviewReqDto.comment());
        Car car = carService.getCarById(reviewReqDto.car_id());
        review.setCar(car);
        reviewRepository.save(review);
    }

    public void deleteReview(int id, String username) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        User user = userService.getByUsername(username);

        boolean isAdmin = user.getRole().equals(Role.ADMIN);

        boolean isOwner =
                review.getCustomer()
                        .getUser()
                        .getUsername()
                        .equals(username);

        if (!isAdmin && !isOwner) {
            throw new RuntimeException("You are not authorized to delete this review");
        }

        reviewRepository.delete(review);
    }
}
