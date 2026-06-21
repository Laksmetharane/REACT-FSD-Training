package com.rent_db.mapper;

import com.rent_db.dto.CustomerPagingDto;
import com.rent_db.dto.CustomerRespDto;
import com.rent_db.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {
    public CustomerRespDto MapEntityToDto(Customer customer) {
        return new CustomerRespDto(
                customer.getId(),
                customer.getName(),
                customer.getMob_no(),
                customer.getEmail(),
                customer.getDl_no(),
                customer.getUser().getId(),
                customer.getUser().getUsername(),
                customer.getAdmin().getId(),
                customer.getAdmin().getName()
        );
    }

    public CustomerPagingDto mapEntityToDto(List<CustomerRespDto> list, Page<Customer> pages) {
        return new CustomerPagingDto(
                pages.getTotalElements(),
                pages.getTotalPages(),
                list
        );
    }
}
