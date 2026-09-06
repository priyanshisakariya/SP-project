package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.reviewproposal.ReviewProposalResponseDTO;
import com.campus.campus_backend.dto.reviewproposal.ReviewStatusRequestDTO;
import com.campus.campus_backend.service.ReviewProposalService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review-proposal")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewProposalController {

    private final ReviewProposalService reviewProposalService;


    public ReviewProposalController(
            ReviewProposalService reviewProposalService) {

        this.reviewProposalService = reviewProposalService;
    }


    // ==========================================
    // GET ALL PROPOSALS
    // ==========================================

    @GetMapping
    public ResponseEntity<List<ReviewProposalResponseDTO>>
    getAllReviewProposals() {

        return ResponseEntity.ok(
                reviewProposalService.getAllReviewProposals()
        );
    }


    // ==========================================
    // GET ONE PROPOSAL
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<ReviewProposalResponseDTO>
    getReviewProposalById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                reviewProposalService
                        .getReviewProposalById(id)
        );
    }


    // ==========================================
    // APPROVE / REJECT / REQUEST CHANGES
    // ==========================================

    @PutMapping("/{id}/status")
    public ResponseEntity<ReviewProposalResponseDTO>
    updateStatus(
            @PathVariable Integer id,
            @RequestBody ReviewStatusRequestDTO request) {

        return ResponseEntity.ok(
                reviewProposalService.updateStatus(
                        id,
                        request.getStatus()
                )
        );
    }
}