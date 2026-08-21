package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.reviewproposal.ReviewProposalResponseDTO;
import com.campus.campus_backend.entity.ReviewProposal;
import com.campus.campus_backend.repository.ReviewProposalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewProposalService {

    private final ReviewProposalRepository reviewProposalRepository;


    public ReviewProposalService(
            ReviewProposalRepository reviewProposalRepository) {

        this.reviewProposalRepository = reviewProposalRepository;
    }


    // GET ALL REVIEW PROPOSALS

    public List<ReviewProposalResponseDTO> getAllReviewProposals() {

        return reviewProposalRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // GET ONE REVIEW PROPOSAL

    public ReviewProposalResponseDTO getReviewProposalById(
            Integer reviewProposalId) {

        ReviewProposal proposal =
                reviewProposalRepository.findById(reviewProposalId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Review proposal not found with id: "
                                                + reviewProposalId
                                )
                        );

        return convertToDTO(proposal);
    }


    // UPDATE STATUS

    public ReviewProposalResponseDTO updateStatus(
            Integer reviewProposalId,
            String status) {

        ReviewProposal proposal =
                reviewProposalRepository.findById(reviewProposalId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Review proposal not found with id: "
                                                + reviewProposalId
                                )
                        );


        // Validate status

        if (!status.equals("PENDING")
                && !status.equals("APPROVED")
                && !status.equals("REJECTED")
                && !status.equals("CHANGES_REQUESTED")) {

            throw new IllegalArgumentException(
                    "Invalid status. Allowed values: "
                            + "PENDING, APPROVED, REJECTED, CHANGES_REQUESTED"
            );
        }


        proposal.setStatus(status);

        // Only set review time when Faculty actually reviews it

        if (!status.equals("PENDING")) {
            proposal.setReviewedAt(LocalDateTime.now());
        }

        ReviewProposal updatedProposal =
                reviewProposalRepository.save(proposal);

        return convertToDTO(updatedProposal);
    }


    // CONVERT ENTITY → DTO

    private ReviewProposalResponseDTO convertToDTO(
            ReviewProposal proposal) {

        ReviewProposalResponseDTO dto =
                new ReviewProposalResponseDTO();

        dto.setReviewProposalId(
                proposal.getReviewProposalId()
        );

        dto.setProposalId(
                proposal.getProposalId()
        );

        dto.setStudentId(
                proposal.getStudentId()
        );

        dto.setProjectTitle(
                proposal.getProjectTitle()
        );

        dto.setProjectDomain(
                proposal.getProjectDomain()
        );

        dto.setTechnologyStack(
                proposal.getTechnologyStack()
        );

        dto.setOtherTechnology(
                proposal.getOtherTechnology()
        );

        dto.setMember2Name(
                proposal.getMember2Name()
        );

        dto.setMember2Enrollment(
                proposal.getMember2Enrollment()
        );

        dto.setMember3Name(
                proposal.getMember3Name()
        );

        dto.setMember3Enrollment(
                proposal.getMember3Enrollment()
        );

        dto.setProjectDescription(
                proposal.getProjectDescription()
        );

        dto.setProposalFile(
                proposal.getProposalFile()
        );

        dto.setStatus(
                proposal.getStatus()
        );

        dto.setComments(
                proposal.getComments()
        );

        dto.setReviewedAt(
                proposal.getReviewedAt()
        );

        return dto;
    }
}