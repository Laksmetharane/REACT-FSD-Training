package com.rent_db.controller;

import com.rent_db.dto.DashBoardStatsDto;
import com.rent_db.service.DashBoardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@AllArgsConstructor
@CrossOrigin("http://localhost:5173")
public class DashBoardController {
    private final DashBoardService dashBoardService;
    @GetMapping("/stats")
    public DashBoardStatsDto getStats(){
         return dashBoardService.getStats();
    }
}
