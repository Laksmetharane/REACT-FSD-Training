package com.rent_db.controller;

import com.rent_db.dto.PaymentPagingDto;
import com.rent_db.dto.PaymentReqDto;
import com.rent_db.dto.PaymentRespDto;
import com.rent_db.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {
    private PaymentService paymentService;

    @PostMapping("/pay")
    public void pay(
            @RequestBody PaymentReqDto dto){
                paymentService.makePayment(dto);

    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<PaymentRespDto>approve(@PathVariable int id){
        return ResponseEntity.ok(paymentService.approve(id));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<PaymentRespDto>reject(@PathVariable int id){
        return ResponseEntity.ok(paymentService.reject(id));
    }

    @GetMapping("/all")
    public PaymentPagingDto getAll(@RequestParam int page,@RequestParam int size){
        return paymentService.getAll(page,size);
    }

    @GetMapping("/get-one/{id}")
    public ResponseEntity<PaymentRespDto>getById(@PathVariable int id){
        return ResponseEntity.ok(paymentService.getById(id));
    }

}
