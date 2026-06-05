package com.project.controller;


import com.project.dto.LoginResponseDto;
import com.project.dto.SeekerReqDto;
import com.project.dto.TokenDto;
import com.project.dto.EmployerReqDto;
import com.project.model.User;
import com.project.service.EmployerService;
import com.project.service.SeekerService;
import com.project.service.UserService;
import com.project.util.JwtUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtUtility jwtUtility;
    private final UserService userService;
    private final EmployerService employerService;
    private final SeekerService seekerService;

    @GetMapping("/login")
    public TokenDto login(Principal principal){
        String username = principal.getName();
        String token = jwtUtility.generateToken(username);
        return new TokenDto(username,token);
    }

    @GetMapping("/user-details")
    public LoginResponseDto getUserDetails(Principal principal){
        User user = (User)userService.loadUserByUsername(principal.getName());
        return new LoginResponseDto(user.getId(),user.getUsername(),user.getRole().toString());
    }

    @PostMapping("/register/employer")
    public void EmployerRegister(@Valid @RequestBody EmployerReqDto employerReqDto){
        employerService.EmployerRegister(employerReqDto);
    }

    @PostMapping("/register/seeker")
    public void SeekerRegister(@Valid @RequestBody SeekerReqDto seekerReqDto){
        seekerService.SeekerRegister(seekerReqDto);
    }

}
