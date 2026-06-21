package com.rent_db.controller;

import com.rent_db.dto.UserDto;
import com.rent_db.dto.UserRespDto;
import com.rent_db.enums.Role;
import com.rent_db.model.User;
import com.rent_db.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


@GetMapping("/get-one/{id}")
    public UserRespDto getById(@PathVariable int id){

    return  userService.getById(id);
}

public User getUserById(int id){
    return userService.getUserById(id);
}



@GetMapping("/getByRole")
public ResponseEntity<List<UserRespDto>> getByRole(@RequestParam Role role){
    return ResponseEntity.ok(userService.getByRole(role));
}


@PutMapping("/update/{id}")
    public User updateUser(@PathVariable int id,
                           @RequestBody UserDto dto) {
    return userService.updateUser(id, dto);
}


@DeleteMapping("/delete/{id}")
public String deleteUser(@PathVariable int id) {
    userService.deleteUser(id);
    return "User deleted successfully";
}

}
