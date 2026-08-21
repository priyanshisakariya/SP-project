package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.praporsal.ProposalRequestDTO;
import com.campus.campus_backend.dto.praporsal.ProposalResponseDTO;
import com.campus.campus_backend.entity.SubmitProposal;
import com.campus.campus_backend.repository.SubmitProposalRepository;
import com.campus.campus_backend.service.SubmitProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proposal")
@CrossOrigin(origins = "http://localhost:5173")
public class SubmitProposalController {
    @Autowired
    private SubmitProposalService submitProposalService;

    // Submit Project Proposal
    @PostMapping("/submit")
    public ProposalResponseDTO submitProposal(
            @RequestBody ProposalRequestDTO requestDTO){
        System.out.println("proposal submitted");
        return submitProposalService.submitProposal(requestDTO);

    }

}
