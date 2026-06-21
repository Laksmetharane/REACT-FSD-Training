package com.rent_db.controller;

import com.rent_db.dto.AdminDto;
import com.rent_db.dto.AdminPageRespDto;
import com.rent_db.dto.AdminRespDto;
import com.rent_db.model.Admin;
import com.rent_db.model.Customer;
import com.rent_db.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private AdminService adminService;

    @GetMapping("/all")
    public List<Admin> getAll(){
        return adminService.getAll();
    }

    @GetMapping("/paginated/all")
    public AdminPageRespDto getAllAdminByPagination(@RequestParam int page, @RequestParam int size){
        return adminService.getAllAdminByPagination(page,size);
    }

    @PostMapping("/add")
    public void addAdmin(@RequestBody AdminDto adminDto){
        adminService.addAdmin(adminDto);
    }

    @GetMapping("/get-one")
    public AdminRespDto getByAdmin(Principal principal){
        String username = principal.getName();
        return adminService.getByAdmin(username);
    }

//    public Admin getAdminById(int id){
//        return adminService.getAdminById(id);
//    }

    @PutMapping("/update")
    public Admin updateAdmin(Principal principal,
                             @RequestBody AdminDto adminDto) {
        String username = principal.getName();
        return adminService.updateAdmin(username, adminDto);
    }

    @DeleteMapping("/delete")
    public String deleteAdmin(Principal principal) {
        String username = principal.getName();
        adminService.deleteAdmin(username);
        return "Admin deleted successfully";
    }


}
