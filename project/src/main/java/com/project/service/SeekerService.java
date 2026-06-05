package com.project.service;

import com.project.dto.SeekerReqDto;
import com.project.enums.Role;
import com.project.mapper.SeekerMapper;
import com.project.mapper.UserMapper;
import com.project.model.Seeker;
import com.project.model.User;
import com.project.repository.SeekerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeekerService {
    private SeekerRepository seekerRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    public void SeekerRegister(@Valid SeekerReqDto seekerReqDto) {
        Seeker seeker = SeekerMapper.MapDtoToEntity(seekerReqDto);
        User user = UserMapper.MapSeekerDtoToEntity(seekerReqDto);
        user.setRole(Role.JOBSEEKER);
        user.setPassword(passwordEncoder.encode(seekerReqDto.password()));
        user= userService.save(user);
        seeker.setUser(user);
        seekerRepository.save(seeker);
    }

    public Seeker getByUsername(String sUsername) {
        return seekerRepository.findByUserUsername(sUsername);
    }
}
