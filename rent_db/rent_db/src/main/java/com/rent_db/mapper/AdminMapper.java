package com.rent_db.mapper;

import com.rent_db.dto.AdminPageRespDto;
import com.rent_db.dto.AdminRespDto;
import com.rent_db.model.Admin;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminMapper {
    public AdminRespDto MapEntityToDto(Admin admin) {
        return new AdminRespDto(
                admin.getId(),
                admin.getAge(),
                admin.getName()
        );
    }

    public AdminPageRespDto mapEntityToDto(List<AdminRespDto> list, Page<Admin> pages) {
        return new AdminPageRespDto(
                pages.getTotalElements(),
                pages.getTotalPages(),
                list
        );
    }
}
