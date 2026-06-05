package com.project.service;

import com.project.dto.EmployerReqDto;
import com.project.enums.Role;
import com.project.mapper.EmployerMapper;
import com.project.mapper.UserMapper;
import com.project.model.Employer;
import com.project.model.User;
import com.project.repository.EmployerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployerService {
    private EmployerRepository employerRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public void EmployerRegister(@Valid EmployerReqDto employerReqDto) {
        Employer employer = EmployerMapper.MapDtoToEntity(employerReqDto);
        User user = UserMapper.MapDtoToEntity(employerReqDto);
        user.setRole(Role.EMPLOYER);
        user.setPassword(passwordEncoder.encode(employerReqDto.password()));
        user = userService.save(user);
        employer.setUser(user);
        employerRepository.save(employer);
    }

    public Employer getByUsername(String eUsername) {
        return employerRepository.findByUserUsername(eUsername);
    }
}
