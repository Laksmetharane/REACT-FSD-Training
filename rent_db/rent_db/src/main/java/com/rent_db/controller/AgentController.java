package com.rent_db.controller;
import com.rent_db.dto.AgentDto;
import com.rent_db.dto.AgentPagingDto;
import com.rent_db.dto.AgentRespDto;
import com.rent_db.model.RentalAgent;
import com.rent_db.service.AgentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.loadtime.Agent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/agent")
@CrossOrigin(origins="http://localhost:5173")
public class AgentController {
    private AgentService agentService;

    @GetMapping("/all")
    public List<RentalAgent> getAll(){
            return agentService.getAll();
        }

        @GetMapping("/paginated/all")
        public AgentPagingDto getAllAgentByPagination(@RequestParam int page, @RequestParam int size){
            return agentService.getAllAgentByPagination(page,size);
        }

        @PostMapping("/add")
        public void addAgent(@Valid @RequestBody AgentDto agentDto){
            agentService.addAgent(agentDto);
        }

        @GetMapping("/get-one")
        public AgentRespDto getByAgent(Principal principal){
            String username = principal.getName();
            return agentService.getByAgent(username);
        }

//    public Admin getAdminById(int id){
//        return adminService.getAdminById(id);
//    }

        @PutMapping("/update")
        public void updateAgent(Principal principal,
                                 @RequestBody AgentDto adminDto) {
            String username = principal.getName();
            agentService.updateAgent(username, adminDto);
        }

        @DeleteMapping("/delete")
        public String deleteAgent(Principal principal) {
            String username = principal.getName();
            agentService.deleteAgent(username);
            return "Admin deleted successfully";
        }

        @DeleteMapping("/soft-delete/{id}")
        public void softDelete(@PathVariable int id){
                 agentService.softDelete(id);
        }


    }
