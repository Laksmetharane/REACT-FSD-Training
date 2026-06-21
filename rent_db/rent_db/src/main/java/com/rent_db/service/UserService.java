package com.rent_db.service;

import com.rent_db.dto.UserDto;
import com.rent_db.dto.UserRespDto;
import com.rent_db.enums.Role;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.mapper.UserMapper;
import com.rent_db.model.User;
import com.rent_db.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import java.lang.ScopedValue;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
public final UserRepository userRepository;
public final UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Invalid Credentials"));
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public UserRespDto getById(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid id given"));
        return userMapper.mapUserDtoToEntity(user);
    }

    public User updateUser(int id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRole(dto.role());

        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    public List<UserRespDto> getByRole(Role role) {
        List<User> user = userRepository.findByRole(role);
        return user.stream().map(userMapper::mapUserDtoToEntity).toList();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid message"));
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("Invalid name"));
    }


}
