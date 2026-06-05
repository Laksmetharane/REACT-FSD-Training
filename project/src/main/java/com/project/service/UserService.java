package com.project.service;


import com.project.dto.UserRespDto;
import com.project.exception.ResourceNotFoundException;
import com.project.mapper.UserMapper;
import com.project.model.User;
import com.project.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return user;
    }
    public User save(User user) {

        return userRepository.save(user);
    }
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid message"));
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
