package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.praporsal.ProposalRequestDTO;
import com.campus.campus_backend.dto.praporsal.ProposalResponseDTO;
import com.campus.campus_backend.entity.Student;
import com.campus.campus_backend.entity.SubmitProposal;
import com.campus.campus_backend.repository.StudentRepository;
import com.campus.campus_backend.repository.SubmitProposalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmitProposalService {

    @Autowired
    private SubmitProposalRepository submitProposalRepository;

    @Autowired
    private StudentRepository studentRepository;


    // =========================================================
    // SUBMIT PROPOSAL
    // =========================================================

    public ProposalResponseDTO submitProposal(ProposalRequestDTO requestDTO) {

        // =========================================================
        // 1. STUDENT VALIDATION
        // =========================================================

        Student student = studentRepository.findById(requestDTO.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found.")
                );


        // =========================================================
        // 2. ONE PROPOSAL PER STUDENT
        // =========================================================

        if (submitProposalRepository.existsByStudent(student)) {
            throw new RuntimeException(
                    "You have already submitted a project proposal."
            );
        }


        // =========================================================
        // 3. PROJECT TITLE VALIDATION
        // =========================================================

        if (requestDTO.getProjectTitle() == null ||
                requestDTO.getProjectTitle().trim().isEmpty()) {

            throw new RuntimeException("Project title is required.");
        }

        String projectTitle = requestDTO.getProjectTitle().trim();

        if (projectTitle.length() < 5 ||
                projectTitle.length() > 200) {

            throw new RuntimeException(
                    "Project title must be between 5 and 200 characters."
            );
        }


        // =========================================================
        // 4. DUPLICATE PROJECT TITLE
        // =========================================================

        if (submitProposalRepository.existsByProjectTitle(projectTitle)) {
            throw new RuntimeException(
                    "Project title already exists."
            );
        }


        // =========================================================
        // 5. PROJECT DOMAIN VALIDATION
        // =========================================================

        if (requestDTO.getProjectDomain() == null ||
                requestDTO.getProjectDomain().trim().isEmpty()) {

            throw new RuntimeException(
                    "Please select project domain."
            );
        }

        String projectDomain =
                requestDTO.getProjectDomain().trim();


        // =========================================================
        // 6. GUIDE NAME VALIDATION
        // =========================================================

        if (requestDTO.getGuideName() == null ||
                requestDTO.getGuideName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Guide name is required."
            );
        }

        String guideName =
                requestDTO.getGuideName().trim();

        if (guideName.length() < 3) {
            throw new RuntimeException(
                    "Guide name is too short."
            );
        }


        // =========================================================
        // 7. TECHNOLOGY STACK VALIDATION
        // =========================================================

        if (requestDTO.getTechnologyStack() == null ||
                requestDTO.getTechnologyStack().trim().isEmpty()) {

            throw new RuntimeException(
                    "Select at least one technology."
            );
        }

        String technologyStack =
                requestDTO.getTechnologyStack().trim();


        // =========================================================
        // 8. PROJECT DESCRIPTION VALIDATION
        // =========================================================

        if (requestDTO.getProjectDescription() == null ||
                requestDTO.getProjectDescription().trim().isEmpty()) {

            throw new RuntimeException(
                    "Project description is required."
            );
        }

        String projectDescription =
                requestDTO.getProjectDescription().trim();

        if (projectDescription.length() < 30) {
            throw new RuntimeException(
                    "Project description must contain at least 30 characters."
            );
        }

        if (projectDescription.length() > 2000) {
            throw new RuntimeException(
                    "Project description cannot exceed 2000 characters."
            );
        }


        // =========================================================
        // 9. PROPOSAL FILE VALIDATION
        // =========================================================

        if (requestDTO.getProposalFile() == null ||
                requestDTO.getProposalFile().trim().isEmpty()) {

            throw new RuntimeException(
                    "Proposal file is required."
            );
        }

        String proposalFile =
                requestDTO.getProposalFile().trim();

        if (!proposalFile.toLowerCase().endsWith(".pdf")) {
            throw new RuntimeException(
                    "Only PDF proposal files are allowed."
            );
        }


        // =========================================================
        // 10. MEMBER 2 VALIDATION
        // =========================================================

        if (requestDTO.getMember2Name() != null &&
                !requestDTO.getMember2Name().trim().isEmpty()) {

            if (requestDTO.getMember2Enrollment() == null ||
                    requestDTO.getMember2Enrollment().trim().isEmpty()) {

                throw new RuntimeException(
                        "Member 2 enrollment number is required."
                );
            }

            if (requestDTO.getMember2Enrollment()
                    .trim()
                    .equals(student.getEnrollmentNo())) {

                throw new RuntimeException(
                        "You cannot add yourself as Member 2."
                );
            }
        }


        // =========================================================
        // 11. MEMBER 3 VALIDATION
        // =========================================================

        if (requestDTO.getMember3Name() != null &&
                !requestDTO.getMember3Name().trim().isEmpty()) {

            if (requestDTO.getMember3Enrollment() == null ||
                    requestDTO.getMember3Enrollment().trim().isEmpty()) {

                throw new RuntimeException(
                        "Member 3 enrollment number is required."
                );
            }

            if (requestDTO.getMember3Enrollment()
                    .trim()
                    .equals(student.getEnrollmentNo())) {

                throw new RuntimeException(
                        "You cannot add yourself as Member 3."
                );
            }
        }


        // =========================================================
        // 12. DUPLICATE TEAM MEMBER VALIDATION
        // =========================================================

        if (requestDTO.getMember2Enrollment() != null &&
                requestDTO.getMember3Enrollment() != null &&
                !requestDTO.getMember2Enrollment().trim().isEmpty() &&
                !requestDTO.getMember3Enrollment().trim().isEmpty() &&
                requestDTO.getMember2Enrollment()
                        .trim()
                        .equals(
                                requestDTO.getMember3Enrollment().trim()
                        )) {

            throw new RuntimeException(
                    "Duplicate team members are not allowed."
            );
        }


        // =========================================================
        // 13. CREATE PROPOSAL ENTITY
        // =========================================================

        SubmitProposal proposal = new SubmitProposal();

        proposal.setStudent(student);

        proposal.setProjectTitle(projectTitle);

        proposal.setProjectDomain(projectDomain);

        proposal.setGuideName(guideName);

        proposal.setTechnologyStack(technologyStack);

        proposal.setOtherTechnology(
                requestDTO.getOtherTechnology() == null
                        ? null
                        : requestDTO.getOtherTechnology().trim()
        );


        // Member 2

        proposal.setMember2Name(
                requestDTO.getMember2Name() == null
                        ? null
                        : requestDTO.getMember2Name().trim()
        );

        proposal.setMember2Enrollment(
                requestDTO.getMember2Enrollment() == null
                        ? null
                        : requestDTO.getMember2Enrollment().trim()
        );


        // Member 3

        proposal.setMember3Name(
                requestDTO.getMember3Name() == null
                        ? null
                        : requestDTO.getMember3Name().trim()
        );

        proposal.setMember3Enrollment(
                requestDTO.getMember3Enrollment() == null
                        ? null
                        : requestDTO.getMember3Enrollment().trim()
        );


        // Project Description

        proposal.setProjectDescription(projectDescription);


        // Proposal PDF

        proposal.setProposalFile(proposalFile);


        // =========================================================
        // 14. SAVE PROPOSAL
        // =========================================================

        SubmitProposal savedProposal =
                submitProposalRepository.save(proposal);


        // =========================================================
        // 15. CREATE RESPONSE DTO
        // =========================================================

        ProposalResponseDTO responseDTO =
                new ProposalResponseDTO();

        responseDTO.setProposalId(
                savedProposal.getProposalId()
        );

        responseDTO.setStudentId(
                savedProposal.getStudent().getId()
        );

        responseDTO.setProjectTitle(
                savedProposal.getProjectTitle()
        );

        responseDTO.setProjectDomain(
                savedProposal.getProjectDomain()
        );

        responseDTO.setGuideName(
                savedProposal.getGuideName()
        );

        responseDTO.setTechnologyStack(
                savedProposal.getTechnologyStack()
        );

        responseDTO.setOtherTechnology(
                savedProposal.getOtherTechnology()
        );

        responseDTO.setMember2Name(
                savedProposal.getMember2Name()
        );

        responseDTO.setMember2Enrollment(
                savedProposal.getMember2Enrollment()
        );

        responseDTO.setMember3Name(
                savedProposal.getMember3Name()
        );

        responseDTO.setMember3Enrollment(
                savedProposal.getMember3Enrollment()
        );

        responseDTO.setProjectDescription(
                savedProposal.getProjectDescription()
        );

        responseDTO.setProposalFile(
                savedProposal.getProposalFile()
        );


        // =========================================================
        // 16. RETURN RESPONSE
        // =========================================================

        return responseDTO;
    }


    // =========================================================
    // GET PROPOSAL BY STUDENT
    // =========================================================

    public ProposalResponseDTO getProposalByStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found.")
                );

        SubmitProposal proposal =
                submitProposalRepository.findByStudent(student)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Proposal not found for this student."
                                )
                        );


        // =========================================================
        // CREATE RESPONSE DTO
        // =========================================================

        ProposalResponseDTO responseDTO =
                new ProposalResponseDTO();

        responseDTO.setProposalId(
                proposal.getProposalId()
        );

        responseDTO.setStudentId(
                proposal.getStudent().getId()
        );

        responseDTO.setProjectTitle(
                proposal.getProjectTitle()
        );

        responseDTO.setProjectDomain(
                proposal.getProjectDomain()
        );

        responseDTO.setGuideName(
                proposal.getGuideName()
        );

        responseDTO.setTechnologyStack(
                proposal.getTechnologyStack()
        );

        responseDTO.setOtherTechnology(
                proposal.getOtherTechnology()
        );

        responseDTO.setMember2Name(
                proposal.getMember2Name()
        );

        responseDTO.setMember2Enrollment(
                proposal.getMember2Enrollment()
        );

        responseDTO.setMember3Name(
                proposal.getMember3Name()
        );

        responseDTO.setMember3Enrollment(
                proposal.getMember3Enrollment()
        );

        responseDTO.setProjectDescription(
                proposal.getProjectDescription()
        );

        responseDTO.setProposalFile(
                proposal.getProposalFile()
        );


        return responseDTO;
    }
}