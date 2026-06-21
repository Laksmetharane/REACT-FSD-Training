package com.rent_db.service;

import com.rent_db.dto.AdminDto;
import com.rent_db.dto.AdminPageRespDto;
import com.rent_db.dto.AdminRespDto;
import com.rent_db.enums.Role;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.mapper.AdminMapper;
import com.rent_db.model.Admin;
import com.rent_db.model.User;
import com.rent_db.repository.AdminRepository;
import com.rent_db.util.FileUtility;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AdminMapper adminMapper;
    private static final String UPLOAD_LOC = "D:/car_images_file";

    public List<Admin> getAll() {
        List<Admin> list = adminRepository.findAll();
        return list;
    }

    public void addAdmin(AdminDto adminDto) {
        String username = adminDto.user_name();
        String password = adminDto.password();
        Role role = Role.ADMIN;

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(role);

        user = userService.save(user);

        Admin admin = new Admin();
        admin.setName(adminDto.name());
        admin.setAge(adminDto.age());
        admin.setUser(user);
        adminRepository.save(admin);

    }

    public AdminRespDto getByAdmin(String username) {
        Admin admin = getByUsername(username);
        return adminMapper.MapEntityToDto(admin);
    }

    public Admin updateAdmin(String username, AdminDto adminDto) {
        Admin admin = getByUsername(username);
        admin.setAge(adminDto.age());
        return adminRepository.save(admin);
    }

    public void deleteAdmin(String username) {
        Admin admin = getByUsername(username);
        adminRepository.delete(admin);
    }

    public Admin getAdminById(int id) {
        return adminRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Invalid id"));
    }

    public Admin getByUsername(String adminUsername) {
        return adminRepository.findByUserUsername(adminUsername);
    }


    public AdminPageRespDto getAllAdminByPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Admin> pages = adminRepository.findAll(pageable);
        List<AdminRespDto>list = pages.stream().map(adminMapper::MapEntityToDto).toList();
        return adminMapper.mapEntityToDto(list,pages);
    }
}
