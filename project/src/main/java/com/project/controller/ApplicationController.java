package com.project.controller;

import com.project.dto.ApplicationPagingDto;
import com.project.dto.ApplicationReqDto;
import com.project.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/applications/{jobId}")
    public void applyJob(@Valid @RequestBody ApplicationReqDto applicationReqDto, Principal principal,@PathVariable int jobId){
        String S_Username = principal.getName();
        applicationService.applyJob(applicationReqDto,S_Username,jobId);
    }

    @GetMapping("/my-applications")
    public ApplicationPagingDto getOwnApplications(Principal principal,@RequestParam int page,@RequestParam int size){
        String username = principal.getName();
        return applicationService.getOwnApplications(username,page,size);
    }

}
