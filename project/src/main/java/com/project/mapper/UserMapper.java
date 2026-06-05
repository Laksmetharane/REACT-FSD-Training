package com.project.mapper;


import com.project.dto.EmployerReqDto;
import com.project.dto.SeekerReqDto;
import com.project.dto.UserRespDto;
import com.project.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static User MapDtoToEntity(@Valid EmployerReqDto employerReqDto) {
        User user = new User();
        user.setUsername(employerReqDto.username());
        user.setPassword(employerReqDto.password());
        return user;
    }


    public static User MapSeekerDtoToEntity(@Valid SeekerReqDto seekerReqDto) {
        User user = new User();
        user.setUsername(seekerReqDto.username());
        user.setPassword(seekerReqDto.password());
        return user;
    }
}
