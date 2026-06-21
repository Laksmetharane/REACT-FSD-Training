package com.rent_db.controller;


import com.rent_db.dto.LoginResponseDto;
import com.rent_db.dto.TokenDto;
import com.rent_db.model.User;
import com.rent_db.service.UserService;
import com.rent_db.util.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins="http://localhost:5173")
public class AuthController {
    private final JwtUtility jwtUtility;
    private final UserService userService;

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
}
