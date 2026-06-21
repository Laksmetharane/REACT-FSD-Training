package com.rent_db.controller;


import com.rent_db.dto.ReviewReqDto;
import com.rent_db.dto.ReviewRespDto;
import com.rent_db.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/review")
@CrossOrigin(origins="http://localhost:5173")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/all")
    public List<ReviewRespDto> getAll(){
        return reviewService.getAll();
    }

    @GetMapping("/get-one/{id}")
    public ReviewRespDto getById(@PathVariable int id){
         return reviewService.getById(id);
    }

    @GetMapping("/getByRating")
    public List<ReviewRespDto>getByRating(@RequestParam int rating){
        return reviewService.getByRating(rating);
    }

    @GetMapping("/getByCarId/{id}")
    public List<ReviewRespDto> getByCarId(@PathVariable int id){
        return reviewService.getByCarId(id);
    }

    @GetMapping("/getByCustomer")
    public List<ReviewRespDto>getByCustomer(Principal principal){
        String username = principal.getName();
        return reviewService.getByCustomer(username);
    }

    @PostMapping("/add")
    public void addReview(@RequestBody ReviewReqDto reviewReqDto,Principal principal){
        String username = principal.getName();
        reviewService.addReview(reviewReqDto,username);
    }

    @PutMapping("/update/{id}")
    public void updateReview(@PathVariable int id,
                             @RequestBody ReviewReqDto reviewReqDto,
                             Principal principal) {

        String username = principal.getName();
        reviewService.updateReview(id, reviewReqDto, username);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReview(@PathVariable int id,
                             Principal principal) {

        String username = principal.getName();
        reviewService.deleteReview(id, username);
    }


}
