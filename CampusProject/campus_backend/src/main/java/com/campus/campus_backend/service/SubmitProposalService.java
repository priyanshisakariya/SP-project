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

    public ProposalResponseDTO submitProposal(ProposalRequestDTO requestDTO) {

        // =========================================================
        // 1. Student must exist
        // =========================================================

        Student student = studentRepository.findById(requestDTO.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found.")
                );


        // =========================================================
        // 2. A student should not submit multiple proposals
        // =========================================================

        if (submitProposalRepository.existsByStudent(student)) {
            throw new RuntimeException(
                    "You have already submitted a project proposal."
            );
        }


        // =========================================================
        // 3. Project Title Validation
        // =========================================================

        if (requestDTO.getProjectTitle() == null ||
                requestDTO.getProjectTitle().trim().isEmpty()) {

            throw new RuntimeException("Project title is required.");
        }

        if (requestDTO.getProjectTitle().trim().length() < 5 ||
                requestDTO.getProjectTitle().trim().length() > 200) {

            throw new RuntimeException(
                    "Project title must be between 5 and 200 characters."
            );
        }


        // =========================================================
        // 4. Duplicate Project Title Validation
        // =========================================================

        if (submitProposalRepository.existsByProjectTitle(
                requestDTO.getProjectTitle().trim())) {

            throw new RuntimeException(
                    "Project title already exists."
            );
        }


        // =========================================================
        // 5. Project Domain Validation
        // =========================================================

        if (requestDTO.getProjectDomain() == null ||
                requestDTO.getProjectDomain().trim().isEmpty()) {

            throw new RuntimeException(
                    "Please select project domain."
            );
        }


        // =========================================================
        // 6. Guide Name Validation
        // =========================================================

        if (requestDTO.getGuideName() == null ||
                requestDTO.getGuideName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Guide name is required."
            );
        }

        if (requestDTO.getGuideName().trim().length() < 3) {

            throw new RuntimeException(
                    "Guide name is too short."
            );
        }


        // =========================================================
        // 7. Technology Stack Validation
        // =========================================================

        if (requestDTO.getTechnologyStack() == null ||
                requestDTO.getTechnologyStack().trim().isEmpty()) {

            throw new RuntimeException(
                    "Select at least one technology."
            );
        }


        // =========================================================
        // 8. Project Description Validation
        // =========================================================

        if (requestDTO.getProjectDescription() == null ||
                requestDTO.getProjectDescription().trim().isEmpty()) {

            throw new RuntimeException(
                    "Project description is required."
            );
        }

        if (requestDTO.getProjectDescription().trim().length() < 30) {

            throw new RuntimeException(
                    "Project description must contain at least 30 characters."
            );
        }

        if (requestDTO.getProjectDescription().trim().length() > 2000) {

            throw new RuntimeException(
                    "Project description cannot exceed 2000 characters."
            );
        }


        // =========================================================
        // 9. Proposal File Validation
        // =========================================================

        if (requestDTO.getProposalFile() == null ||
                requestDTO.getProposalFile().trim().isEmpty()) {

            throw new RuntimeException(
                    "Proposal file is required."
            );
        }

        if (!requestDTO.getProposalFile()
                .toLowerCase()
                .endsWith(".pdf")) {

            throw new RuntimeException(
                    "Only PDF proposal files are allowed."
            );
        }


        // =========================================================
        // 10. Member 2 Validation
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
        // 11. Member 3 Validation
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
        // 12. Duplicate Team Member Validation
        // =========================================================

        if (requestDTO.getMember2Enrollment() != null &&
                requestDTO.getMember3Enrollment() != null &&
                requestDTO.getMember2Enrollment()
                        .trim()
                        .equals(requestDTO.getMember3Enrollment().trim())) {

            throw new RuntimeException(
                    "Duplicate team members are not allowed."
            );
        }


        // =========================================================
        // 13. Create Proposal Entity
        // =========================================================

        SubmitProposal proposal = new SubmitProposal();

        proposal.setStudent(student);

        proposal.setProjectTitle(
                requestDTO.getProjectTitle().trim()
        );

        proposal.setProjectDomain(
                requestDTO.getProjectDomain().trim()
        );

        proposal.setGuideName(
                requestDTO.getGuideName().trim()
        );

        proposal.setTechnologyStack(
                requestDTO.getTechnologyStack().trim()
        );

        proposal.setOtherTechnology(
                requestDTO.getOtherTechnology() == null
                        ? null
                        : requestDTO.getOtherTechnology().trim()
        );

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

        // ⭐ IMPORTANT FIX
        // This was missing before.
        proposal.setProjectDescription(
                requestDTO.getProjectDescription().trim()
        );

        proposal.setProposalFile(
                requestDTO.getProposalFile().trim()
        );


        // =========================================================
        // 14. Save Proposal
        // =========================================================

        SubmitProposal savedProposal =
                submitProposalRepository.save(proposal);


        // =========================================================
        // 15. Create Response DTO
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
        // 16. Return Response
        // =========================================================

        return responseDTO;
    }
}





//package com.campus.campus_backend.service;
//
//import com.campus.campus_backend.dto.praporsal.ProposalRequestDTO;
//import com.campus.campus_backend.dto.praporsal.ProposalResponseDTO;
//import com.campus.campus_backend.entity.Student;
//import com.campus.campus_backend.entity.SubmitProposal;
//import com.campus.campus_backend.repository.StudentRepository;
//import com.campus.campus_backend.repository.SubmitProposalRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SubmitProposalService {
//
//    @Autowired
//    private SubmitProposalRepository submitProposalRepository;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    public ProposalResponseDTO submitProposal(ProposalRequestDTO requestDTO) {
//
//        // 1. Student must exist
//        Student student = studentRepository.findById(requestDTO.getStudentId())
//                .orElseThrow(() ->
//                        new RuntimeException("Student not found."));
//
//
//        //A student should not be able to submit multiple proposals.
//        if (submitProposalRepository.existsByStudent(student)) {
//            throw new RuntimeException("You have already submitted a project proposal.");
//        }
//
//        if (requestDTO.getProjectTitle() == null ||
//                requestDTO.getProjectTitle().trim().isEmpty()) {
//
//            throw new RuntimeException("Project title is required.");
//        }
//
//        if (requestDTO.getProjectTitle().trim().length() < 5 ||
//                requestDTO.getProjectTitle().trim().length() > 200) {
//
//            throw new RuntimeException("Project title must be between 5 and 200 characters.");
//        }
//
//        // Duplicate Project Title Validation
//        if (submitProposalRepository.existsByProjectTitle(requestDTO.getProjectTitle().trim())) {
//            throw new RuntimeException("Project title already exists.");
//        }
//
//        // 3. Project Domain Validation
//        if (requestDTO.getProjectDomain() == null ||
//                requestDTO.getProjectDomain().trim().isEmpty()) {
//
//            throw new RuntimeException("Please select project domain.");
//        }
//
//        if (requestDTO.getGuideName().trim().length() < 3) {
//            throw new RuntimeException("Guide name is too short.");
//        }
//
//        // 5. Technology Stack Validation
//        if (requestDTO.getTechnologyStack() == null ||
//                requestDTO.getTechnologyStack().trim().isEmpty()) {
//
//            throw new RuntimeException("Select at least one technology.");
//        }
//
//        // 6. Project Description Validation
//        if (requestDTO.getProjectDescription() == null ||
//                requestDTO.getProjectDescription().trim().isEmpty()) {
//
//            throw new RuntimeException("Project description is required.");
//        }
//
//        if (requestDTO.getProjectDescription().trim().length() < 30) {
//            throw new RuntimeException("Project description must contain at least 30 characters.");
//        }
//
//        if (requestDTO.getProjectDescription().trim().length() > 2000) {
//            throw new RuntimeException("Project description cannot exceed 2000 characters.");
//        }
//
//        // 7. Proposal File Validation
//        if (requestDTO.getProposalFile() == null ||
//                requestDTO.getProposalFile().trim().isEmpty()) {
//
//            throw new RuntimeException("Proposal file is required.");
//        }
//
//        if (!requestDTO.getProposalFile().toLowerCase().endsWith(".pdf")) {
//            throw new RuntimeException("Only PDF proposal files are allowed.");
//        }
//        //member 2 validation
//        if (requestDTO.getMember2Name() != null &&
//                !requestDTO.getMember2Name().trim().isEmpty()) {
//
//            if (requestDTO.getMember2Enrollment() == null ||
//                    requestDTO.getMember2Enrollment().trim().isEmpty()) {
//
//                throw new RuntimeException("Member 2 enrollment number is required.");
//            }
//
//            if (requestDTO.getMember2Enrollment().equals(student.getEnrollmentNo())) {
//                throw new RuntimeException("You cannot add yourself as Member 2.");
//            }
//        }
//
//        //member 3 validation
//        if (requestDTO.getMember3Name() != null &&
//                !requestDTO.getMember3Name().trim().isEmpty()) {
//
//            if (requestDTO.getMember3Enrollment() == null ||
//                    requestDTO.getMember3Enrollment().trim().isEmpty()) {
//
//                throw new RuntimeException("Member 3 enrollment number is required.");
//            }
//
//            if (requestDTO.getMember3Enrollment().equals(student.getEnrollmentNo())) {
//                throw new RuntimeException("You cannot add yourself as Member 3.");
//            }
//        }
//
//        //member 3 validation
//        if (requestDTO.getMember3Name() != null &&
//                !requestDTO.getMember3Name().trim().isEmpty()) {
//
//            if (requestDTO.getMember3Enrollment() == null ||
//                    requestDTO.getMember3Enrollment().trim().isEmpty()) {
//
//                throw new RuntimeException("Member 3 enrollment number is required.");
//            }
//
//            if (requestDTO.getMember3Enrollment().equals(student.getEnrollmentNo())) {
//                throw new RuntimeException("You cannot add yourself as Member 3.");
//            }
//        }
//
//        //Duplicate Team Member validation
//        if (requestDTO.getMember2Enrollment() != null &&
//                requestDTO.getMember3Enrollment() != null &&
//                requestDTO.getMember2Enrollment().equals(requestDTO.getMember3Enrollment())) {
//
//            throw new RuntimeException("Duplicate team members are not allowed.");
//        }
//
//
//
//
//        // Create Proposal Entity
//        SubmitProposal proposal = new SubmitProposal();
//
//        proposal.setStudent(student);
//        proposal.setProjectTitle(requestDTO.getProjectTitle().trim());
//        proposal.setProjectDomain(requestDTO.getProjectDomain().trim());
//        proposal.setGuideName(requestDTO.getGuideName().trim());
//        proposal.setTechnologyStack(requestDTO.getTechnologyStack().trim());
//        proposal.setOtherTechnology(requestDTO.getOtherTechnology() == null ? null : requestDTO.getOtherTechnology().trim());
//
//        proposal.setMember2Name(requestDTO.getMember2Name() == null ? null : requestDTO.getMember2Name().trim());
//
//        proposal.setMember2Enrollment(requestDTO.getMember2Enrollment() == null ? null : requestDTO.getMember2Enrollment().trim());
//
//        proposal.setMember3Name(requestDTO.getMember3Name() == null ? null : requestDTO.getMember3Name().trim());
//
//        proposal.setMember3Enrollment(requestDTO.getMember3Enrollment() == null ? null : requestDTO.getMember3Enrollment().trim());
//        proposal.setProposalFile(requestDTO.getProposalFile());
//
//        SubmitProposal savedProposal = submitProposalRepository.save(proposal);
//
//        ProposalResponseDTO responseDTO = new ProposalResponseDTO();
//
//        responseDTO.setProposalId(savedProposal.getProposalId());
//        responseDTO.setStudentId(savedProposal.getStudent().getId());
//
//        responseDTO.setProjectTitle(savedProposal.getProjectTitle());
//        responseDTO.setProjectDomain(savedProposal.getProjectDomain());
//        responseDTO.setGuideName(savedProposal.getGuideName());
//        responseDTO.setTechnologyStack(savedProposal.getTechnologyStack());
//        responseDTO.setOtherTechnology(savedProposal.getOtherTechnology());
//
//        responseDTO.setMember2Name(savedProposal.getMember2Name());
//        responseDTO.setMember2Enrollment(savedProposal.getMember2Enrollment());
//
//        responseDTO.setMember3Name(savedProposal.getMember3Name());
//        responseDTO.setMember3Enrollment(savedProposal.getMember3Enrollment());
//
//        responseDTO.setProjectDescription(savedProposal.getProjectDescription());
//        responseDTO.setProposalFile(savedProposal.getProposalFile());
//
//        return responseDTO;
//    }
//}