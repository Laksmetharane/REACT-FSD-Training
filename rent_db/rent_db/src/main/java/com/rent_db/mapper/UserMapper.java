package com.rent_db.mapper;

import com.rent_db.dto.UserRespDto;
import com.rent_db.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserRespDto mapUserDtoToEntity(User user) {
        return new UserRespDto(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
