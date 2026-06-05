package com.project.controller;

import com.project.dto.EmployerReqDto;
import com.project.dto.UserRespDto;
import com.project.enums.Role;
import com.project.model.User;
import com.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

}
