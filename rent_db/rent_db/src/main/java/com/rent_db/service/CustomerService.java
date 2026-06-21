package com.rent_db.service;

import com.rent_db.dto.*;
import com.rent_db.enums.Role;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.mapper.CustomerMapper;
import com.rent_db.model.Admin;
import com.rent_db.model.Customer;
import com.rent_db.model.User;
import com.rent_db.repository.CustomerRepository;
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
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AdminService adminService;
    private final CustomerMapper customerMapper;
    private static final String UPLOAD_LOC = "C:/Users/ELCOT/MyCarApp/public/images";


    public CustomerPagingDto getAll(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Customer> pages = customerRepository.getAllActive(true,pageable);
        List<CustomerRespDto> list = pages.stream().map(customerMapper::MapEntityToDto).toList();
        return customerMapper.mapEntityToDto(list,pages);
    }

    public CustomerRespDto getByCustomer(String username) {
        Customer customer = getByUsername(username);
        return customerMapper.MapEntityToDto(customer);
    }

    public Customer getCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid id given"));
    }

    public CustomerRespDto getByCustomerName(String name) {
        Customer customer = customerRepository.findByName(name);
        return customerMapper.MapEntityToDto(customer);
    }

    public CustomerRespDto getByDlNo(int dlNo) {
        Customer customer = customerRepository.findByDlNo(dlNo);
        return customerMapper.MapEntityToDto(customer);
    }

    public void addCustomer(CustomerReqDto customerReqDto) {
        String username = customerReqDto.user_name();
        String password = customerReqDto.password();
        Role role = Role.CUSTOMER;
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(role);
        user = userService.save(user);
        Admin admin = adminService.getAdminById(customerReqDto.admin_id());
        Customer customer = new Customer();
        customer.setName(customerReqDto.name());
        customer.setMob_no(customerReqDto.mob_no());
        customer.setEmail(customerReqDto.email());
        customer.setDl_no(customerReqDto.dl_no());
        customer.setDocIdPath(customerReqDto.id_path());
        customer.setRole(role);
        customer.setUser(user);
        customer.setAdmin(admin);
        customerRepository.save(customer);
    }

    public void updateCustomer(
            String username,
            CustomerUpdateDto dto) {

        Customer customer = getByUsername(username);

        customer.setName(dto.name());
        customer.setMob_no(dto.mob_no());
        customer.setEmail(dto.email());
        customer.setDl_no(dto.dl_no());
        customer.setDocIdPath(dto.docIdPath());

        customerRepository.save(customer);
    }

    public void deleteCustomer(int id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }

    public Customer getByUsername(String customerUsername) {
        return customerRepository.findByUserUsername(customerUsername);
    }

    public CustomerPagingDto getCustomerByAdmin(String adminUsername,int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Customer>pages = customerRepository.getCustomerByAdmin(adminUsername,pageable);
        List<CustomerRespDto>list = pages.stream().map(customerMapper::MapEntityToDto).toList();
        return customerMapper.mapEntityToDto(list,pages);
    }

    public String upload(MultipartFile file) throws IOException {
        FileUtility.validateFile(file);
        String fileName = file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_LOC);
        Path destinationPath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(),destinationPath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public void softDelete(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid id"));
        customer.setActive(false);
        customerRepository.save(customer);
    }

    public void changePassword(
            String username,
            ChangePasswordDto dto) {

        User user = userService.getByUsername(username);

        boolean valid =
                passwordEncoder.matches(
                        dto.oldPassword(),
                        user.getPassword());

        if (!valid) {
            throw new RuntimeException(
                    "Current Password Incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(
                        dto.newPassword()));

        userService.save(user);
    }
}
