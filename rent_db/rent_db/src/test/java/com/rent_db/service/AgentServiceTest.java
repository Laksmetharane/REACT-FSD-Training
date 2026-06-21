package com.rent_db.service;


import com.rent_db.dto.AgentDto;
import com.rent_db.dto.AgentPagingDto;
import com.rent_db.exception.ResourceNotFoundException;
import com.rent_db.model.RentalAgent;
import com.rent_db.model.User;
import com.rent_db.repository.AgentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgentServiceTest {
    @Mock
    private AgentRepository agentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AgentService agentService;

    private RentalAgent agent;
    private RentalAgent agent1;
    private RentalAgent agent2;
    private User user;

    @BeforeEach
    public void sampleDate(){
        user = new User();
        user.setId(1);
        user.setUsername("anu123");
        agent = new RentalAgent();
        agent.setId(1);
        agent.setName("Anu");
        agent.setMob_no("34759657");
        agent.setAddress("Ammapet,salem");
        agent.setUser(user);


        User user1 = new User();
        user1.setId(2);
        user1.setUsername("mohana123");
        agent1 = new RentalAgent();
        agent1.setId(2);
        agent1.setName("Mohana");
        agent1.setMob_no("34739457");
        agent1.setAddress("Ammapet,salem");
        agent1.setUser(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("hari123");
        agent2 = new RentalAgent();
        agent2.setId(2);
        agent2.setName("Hari");
        agent2.setMob_no("3848594");
        agent2.setAddress("Ammapet,salem");
        agent2.setActive(true);
        agent2.setUser(user2);

    }

    @Test
    public void getAllAgents_MustReturnSomething(){
        List<RentalAgent>agents = List.of(agent,agent1);
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page,size);
        Page<RentalAgent> agentPage = new PageImpl<>(agents,pageable,2);
        when(agentRepository.getAllActive(eq(true),any(Pageable.class))).thenReturn(agentPage);
        AgentPagingDto actualCall = agentService.getAllAgentByPagination(0,2);
        assertThat(actualCall.totalRecords()).isEqualTo(2);
        assertThat(actualCall.totalPages()).isEqualTo(1);
        assertThat(actualCall.data()).hasSize(2);

        assertThat(actualCall.data().getFirst().name()).isEqualTo("Anu");
        assertThat(actualCall.data().getFirst().address()).isEqualTo("Ammapet,salem");

        assertThat(actualCall.data().get(1).name()).isEqualTo("Mohana");
        assertThat(actualCall.data().get(1).address()).isEqualTo("Ammapet,salem");

        verify (agentRepository,times(1)).getAllActive(eq(true),any(Pageable.class));
    }

    @Test
    public void getAllAgents_ReturnsEmptyList(){
        List<RentalAgent>agents = List.of();
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page,size);
        Page<RentalAgent> agentPage = new PageImpl<>(agents,pageable,0);
        when(agentRepository.getAllActive(eq(true),any(Pageable.class))).thenReturn(agentPage);
        AgentPagingDto actualCall = agentService.getAllAgentByPagination(0,2);
        assertThat(actualCall.totalRecords()).isEqualTo(0);
        assertThat(actualCall.totalPages()).isEqualTo(0);
        assertThat(actualCall.data()).hasSize(0);

    }

    @Test
    void getById_agentsExists(){
        when(agentRepository.findById(100)).thenReturn(Optional.of(agent));
        when(agentRepository.findById(200)).thenReturn(Optional.of(agent1));

        assertThat(agentService.getById(100).getId()).isEqualTo(1);
        assertThat(agentService.getById(200).getId()).isEqualTo(2);

        assertThat(agentService.getById(100).getName()).isEqualTo("Anu");
        assertThat(agentService.getById(200).getName()).isEqualTo("Mohana");

        assertThat(agentService.getById(100).getMob_no()).isEqualTo("34759657");
        assertThat(agentService.getById(200).getMob_no()).isEqualTo("34739457");
    }

    @Test
    void getById_agentDoesNotExist(){
        when(agentRepository.findById(100)).thenReturn(Optional.empty());
        assertThatThrownBy(()->agentService.getById(100)).isInstanceOf(ResourceNotFoundException.class).hasMessage("Invalid Id");

        verify(agentRepository,times(1)).findById(100);
    }

//    @Test
//    void addAgents_mustSaveAndReturnCategory(){
//        when(userService.loadUserByUsername(anyString()))
//                .thenThrow(new UsernameNotFoundException("not found"));
//
//        when(passwordEncoder.encode(anyString()))
//                .thenReturn("encoded123");
//
//        when(userService.save(any(User.class)))
//                .thenReturn(new User());
//        when(agentRepository.save(any(RentalAgent.class))).thenReturn(agent2);
//        AgentDto dto = new AgentDto("hari123","Hari","3848594","Ammapet,salem");
//        RentalAgent actualAgent = agentService.addAgent(dto);
//        assertThat(actualAgent.getName()).isEqualTo(agent2.getName());
//        assertThat(actualAgent.getMob_no()).isEqualTo(agent2.getMob_no());
//        assertThat(actualAgent.getAddress()).isEqualTo(agent2.getAddress());
//
//        verify(agentRepository,times(1)).save(any(RentalAgent.class));
//    }

    @Test
    void deleteAgent_mustDeleteAndReturnNothing(){
        when(agentRepository.findById(300)).thenReturn(Optional.of(agent2));
        when(agentRepository.save(any(RentalAgent.class))).thenReturn(agent2);
        agentService.softDelete(300);
        assertThat(agent2.isActive()).isFalse();
        verify(agentRepository,times(1)).findById(300);
        verify(agentRepository,times(1)).save(agent2);


    }

}
