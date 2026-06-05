package com.project.mapper;

import com.project.dto.EmployerReqDto;
import com.project.model.Employer;
import jakarta.validation.Valid;

public class EmployerMapper {

    public static Employer MapDtoToEntity(@Valid EmployerReqDto employerReqDto) {
        Employer employer = new Employer();
        employer.setCompanyName(employerReqDto.company_name());
        return employer;
    }
}
