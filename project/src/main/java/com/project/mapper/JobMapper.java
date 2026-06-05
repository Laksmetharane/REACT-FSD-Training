package com.project.mapper;

import com.project.dto.CreateJobRequestDto;
import com.project.dto.JobResponse;
import com.project.dto.JobResponseDto;
import com.project.model.Job;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public class JobMapper {

    public static Job mapDtoToEntity(@Valid CreateJobRequestDto dto) {
        Job job = new Job();
        job.setTitle(dto.title());
        job.setDescription(dto.description());
        job.setLocation(dto.location());
        job.setSalary(dto.salary());
        return job;
    }

    public static JobResponse mapEntityToDto(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getLocation(),
                job.getSalary(),
                job.getEmployer().getCompanyName()
        );
    }

    public static JobResponseDto maplistEntityToDto(List<JobResponse> list, Page<Job> jpage) {
        return new JobResponseDto(
                jpage.getTotalPages(),
                jpage.getTotalElements(),
                list
        );
    }
}

