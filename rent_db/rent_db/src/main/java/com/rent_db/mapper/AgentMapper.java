package com.rent_db.mapper;

import com.rent_db.dto.AdminDto;
import com.rent_db.dto.AgentDto;
import com.rent_db.dto.AgentPagingDto;
import com.rent_db.dto.AgentRespDto;
import com.rent_db.model.Admin;
import com.rent_db.model.RentalAgent;
import com.rent_db.model.User;
import org.aspectj.weaver.loadtime.Agent;
import org.springframework.data.domain.Page;

import java.util.List;

public class AgentMapper {
    public static AgentRespDto MapEntityToDto(RentalAgent agent) {
        return new AgentRespDto(
                agent.getId(),
                agent.getName(),
                agent.getUser().getUsername(),
                agent.getMob_no(),
                agent.getAddress()
        );

    }

    public static AgentPagingDto mapEntityToDto(List<AgentRespDto> list, Page<RentalAgent> pages) {
        return new AgentPagingDto(
        pages.getTotalElements(),
        pages.getTotalPages(),
        list
        );
    }

    public static RentalAgent mapEntityToUpdatedEntity(RentalAgent existing, AgentDto agentDto) {
        existing.setName(agentDto.name());
        existing.setMob_no(agentDto.mob_no());
        existing.setAddress(agentDto.address());
        return existing;
    }


}
