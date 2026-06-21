package com.rent_db.mapper;


import com.rent_db.dto.PaymentPagingDto;
import com.rent_db.dto.PaymentRespDto;
import com.rent_db.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentMapper {

    public PaymentRespDto mapToDto(Payment payment) {
        return new PaymentRespDto(
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getReservation().getId()
        );
    }


    public PaymentPagingDto MapToDto(List<PaymentRespDto> list, Page<Payment> pages) {
        return new PaymentPagingDto(
                pages.getTotalElements(),
                pages.getTotalPages(),
                list
        );
    }
}
