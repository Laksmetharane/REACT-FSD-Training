package com.project.service;

import com.project.dto.ApplicationPagingDto;
import com.project.dto.ApplicationReqDto;
import com.project.dto.ApplicationResponseDto;
import com.project.mapper.ApplicationMapper;
import com.project.model.Application;
import com.project.model.Job;
import com.project.model.Seeker;
import com.project.repository.ApplicationRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationService {
    private ApplicationRepository applicationRepository;
    private SeekerService seekerService;
    private JobService jobService;
    //private ApplicationMapper applicationMapper;
    public void applyJob(@Valid ApplicationReqDto applicationReqDto, String sUsername,int jobId) {
        Seeker seeker = seekerService.getByUsername(sUsername);
        Application application = ApplicationMapper.mapDtoToEntity(applicationReqDto);
        Job job = jobService.getById(jobId);
        application.setSeeker(seeker);
        application.setJob(job);
        applicationRepository.save(application);
    }

    public ApplicationPagingDto getOwnApplications(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Application> aPage = applicationRepository.findAllBySeekerUserUsername(username,pageable);
        List<ApplicationResponseDto>list = aPage.stream().map(ApplicationMapper::mapEntityToDto).toList();
        return ApplicationMapper.maplisttoDto(list,aPage);
    }
}
