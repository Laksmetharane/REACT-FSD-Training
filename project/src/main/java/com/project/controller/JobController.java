package com.project.controller;

import com.project.dto.CreateJobRequestDto;
import com.project.dto.JobResponse;
import com.project.dto.JobResponseDto;
import com.project.service.JobService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping("/jobs")
    public void addJob(@Valid @RequestBody CreateJobRequestDto dto, Principal principal){
        String E_username = principal.getName();
        jobService.addJob(dto,E_username);
    }

    @GetMapping("/jobs/all")
    public JobResponseDto getAllJobs(@RequestParam int page,
                                           @RequestParam int size){
        return jobService.getAllJobs(page,size);
    }
}
