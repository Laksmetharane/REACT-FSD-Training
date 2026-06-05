package com.project.mapper;

import com.project.dto.ApplicationPagingDto;
import com.project.dto.ApplicationReqDto;
import com.project.dto.ApplicationResponseDto;
import com.project.model.Application;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public class ApplicationMapper {

    public static Application mapDtoToEntity(@Valid ApplicationReqDto dto) {
        Application application = new Application();
        application.setAppliedAt(dto.appliedAt());
        return application;
    }

    public static ApplicationResponseDto mapEntityToDto(Application application) {
        return new ApplicationResponseDto(
                application.getId(),
                application.getAppliedAt(),
                application.getJob().getTitle(),
                application.getJob().getEmployer().getCompanyName()
        );
    }

    public static ApplicationPagingDto maplisttoDto(List<ApplicationResponseDto> list, Page<Application> aPage) {
        return new ApplicationPagingDto(
                aPage.getTotalPages(),
                aPage.getTotalElements(),
                list
        );
    }
}
