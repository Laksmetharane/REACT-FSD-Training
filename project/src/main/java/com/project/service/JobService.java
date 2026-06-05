package com.project.service;

import com.project.dto.CreateJobRequestDto;
import com.project.dto.JobResponse;
import com.project.dto.JobResponseDto;
import com.project.exception.ResourceNotFoundException;
import com.project.mapper.JobMapper;
import com.project.model.Employer;
import com.project.model.Job;
import com.project.repository.JobRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final EmployerService employerService;

    public void addJob(@Valid CreateJobRequestDto dto, String eUsername) {
        Job job = JobMapper.mapDtoToEntity(dto);
        Employer employer = employerService.getByUsername(eUsername);
        job.setEmployer(employer);
        jobRepository.save(job);
    }

    public Job getById(int jobId) {
        return jobRepository.findById(jobId).orElseThrow(()->new ResourceNotFoundException("Invalid id"));
    }

    public JobResponseDto getAllJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Job>pages = jobRepository.findAll(pageable);
        List<JobResponse>list=pages.stream().map(JobMapper::mapEntityToDto).toList();
        return JobMapper.maplistEntityToDto(list,pages);
    }
}
