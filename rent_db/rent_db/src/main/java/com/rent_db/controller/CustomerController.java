package com.rent_db.controller;

import com.rent_db.dto.*;
import com.rent_db.model.Customer;
import com.rent_db.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
@CrossOrigin(origins="http://localhost:5173")
public class CustomerController {
    private CustomerService customerService;
    @GetMapping("/all")
    public CustomerPagingDto getAll(@RequestParam int page, @RequestParam int size){
        return customerService.getAll(page,size);
    }

    @GetMapping("/get-one")
    public ResponseEntity<CustomerRespDto> getByCustomer(Principal principal){
        String username = principal.getName();
        return ResponseEntity.ok(customerService.getByCustomer(username));
    }

    @GetMapping("/getByName")
    public ResponseEntity<CustomerRespDto>getByCustomerName(@RequestParam String name){
        return ResponseEntity.ok(customerService.getByCustomerName(name));
    }

    @GetMapping("/getBydl_No")
    public ResponseEntity<CustomerRespDto>getByDlNo(@RequestParam int dl_no){
        return ResponseEntity.ok(customerService.getByDlNo(dl_no));
    }

    @GetMapping("/all/for-admin")
    public CustomerPagingDto getCustomerByAdminId(Principal principal,@RequestParam int page,@RequestParam int size){
        String adminUsername = principal.getName();
        return customerService.getCustomerByAdmin(adminUsername,page,size);
    }

    @PostMapping("/add")
    public void addCustomer(@RequestBody CustomerReqDto customerReqDto){
        customerService.addCustomer(customerReqDto);
    }

    @PostMapping("/driving_license/upload")
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile file) throws IOException {
        String fileName = customerService.upload(file);
        return ResponseEntity.ok(fileName);
    }

    @PutMapping("/update")
    public void updateCustomer(Principal principal, @RequestBody CustomerUpdateDto dto) {
        String username = principal.getName();
        customerService.updateCustomer(username,dto);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordDto dto,
            Principal principal) {

        customerService.changePassword(
                principal.getName(),
                dto);

        return ResponseEntity.ok(
                "Password Updated Successfully");
    }

    @DeleteMapping("soft-delete/{id}")
    public void softDelete(@PathVariable int id){
        customerService.softDelete(id);
    }






}
