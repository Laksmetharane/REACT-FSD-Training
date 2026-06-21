package com.rent_db.service;


import com.rent_db.dto.AgentDto;
import com.rent_db.dto.AgentPagingDto;
import com.rent_db.dto.AgentRespDto;
import com.rent_db.enums.Role;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.exception.UserAlreadyPresentException;
import com.rent_db.mapper.AgentMapper;
import com.rent_db.model.RentalAgent;
import com.rent_db.model.User;
import com.rent_db.repository.AgentRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentService {
    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Value("${agent.password.temp}")
    private String agentTempPassword;

    public List<RentalAgent> getAll() {
        List<RentalAgent>list = agentRepository.findAll();
        return list;
    }

    public AgentPagingDto getAllAgentByPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<RentalAgent> pages = agentRepository.getAllActive(true,pageable);
        List<AgentRespDto>list = pages.stream().map(AgentMapper::MapEntityToDto).toList();
        return AgentMapper.mapEntityToDto(list,pages);
    }

    public RentalAgent addAgent(AgentDto agentDto) {
        String username = agentDto.username();
        String password = agentTempPassword;
        Role role = Role.RENTAL_AGENT;

        User user = null;

        try {
            user = (User) userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            System.out.println("Not found");
        }

        if(user != null) {
            throw new UserAlreadyPresentException(
                    "Username is already taken"
            );
        }

        String encodedPassword = passwordEncoder.encode(password);
        user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(role);

        user = userService.save(user);

        RentalAgent agent = new RentalAgent();
        agent.setName(agentDto.name());
        agent.setRole(role);
        agent.setUser(user);
        agent.setMob_no(agentDto.mob_no());
        agent.setAddress(agentDto.address());
        agentRepository.save(agent);
        return agent;
    }

    public AgentRespDto getByAgent(String username) {
        RentalAgent agent = getByUsername(username);
        return AgentMapper.MapEntityToDto(agent);
    }

    public RentalAgent getByUsername(String username) {

        return agentRepository.findByUserUsername(username);
    }

    public void updateAgent(String userName, AgentDto agentDto) {
        RentalAgent existing = getByUsername(userName);
        String U_username = agentDto.username();
        String password = agentTempPassword;
        Role role = Role.RENTAL_AGENT;
        String encodedPassword = passwordEncoder.encode(password);
        User user1 = new User();
        user1.setUsername(U_username);
        user1.setPassword(encodedPassword);
        user1.setRole(role);
        user1 = userService.save(user1);

        RentalAgent agent = AgentMapper.mapEntityToUpdatedEntity(existing,agentDto);
        agent.setUser(user1);
        agentRepository.save(agent);
}
    public void deleteAgent(String username) {
        RentalAgent rentalAgent = getByUsername(username);
        agentRepository.delete(rentalAgent);
    }

    public RentalAgent getById(int i) {
        return agentRepository.findById(i).orElseThrow(()->new ResourceNotFoundException("Invalid Id"));
    }

    public void softDelete(int id) {

        RentalAgent agent = agentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid id"));
        agent.setActive(false);
        agentRepository.save(agent);
    }
}
