package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.generatemarksheet.GenerateMarksheetResponseDTO;
import com.campus.campus_backend.entity.Marks;
import com.campus.campus_backend.entity.SubmitProposal;
import com.campus.campus_backend.repository.GenerateMarksheetRepository;
import com.campus.campus_backend.repository.GenerateMarksheetProposalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenerateMarksheetService {

    private final GenerateMarksheetRepository marksRepository;
    private final GenerateMarksheetProposalRepository proposalRepository;

    public GenerateMarksheetService(
            GenerateMarksheetRepository marksRepository,
            GenerateMarksheetProposalRepository proposalRepository
    ) {
        this.marksRepository = marksRepository;
        this.proposalRepository = proposalRepository;
    }

    // =========================================================
    // GET ALL STUDENTS WHO HAVE MARKS
    // =========================================================

    public List<GenerateMarksheetResponseDTO> getAllStudents() {

        return marksRepository
                .findAllStudentsWithMarks()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // =========================================================
    // GET MARKSHEET FOR ONE STUDENT
    // =========================================================

    public GenerateMarksheetResponseDTO getStudentMarksheet(
            Integer studentId
    ) {

        Marks marks = marksRepository
                .findByStudentId(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Marks not found for student id: "
                                        + studentId
                        )
                );

        return convertToResponse(marks);
    }

    // =========================================================
    // CONVERT MARKS + STUDENT + PROPOSAL
    // =========================================================

    private GenerateMarksheetResponseDTO convertToResponse(
            Marks marks
    ) {

        GenerateMarksheetResponseDTO response =
                new GenerateMarksheetResponseDTO();

        // =====================================================
        // MARKS ID
        // =====================================================

        response.setMarksId(
                marks.getMarksId()
        );

        // =====================================================
        // STUDENT INFORMATION
        // =====================================================

        Integer studentId = null;

        if (marks.getStudent() != null) {

            studentId = marks.getStudent().getId();

            response.setStudentId(
                    studentId
            );

            response.setStudentName(
                    marks.getStudent().getFullName()
            );

            response.setEnrollment(
                    marks.getStudent().getEnrollmentNo()
            );
        }

        // =====================================================
        // PROJECT / PROPOSAL INFORMATION
        // =====================================================

        if (studentId != null) {

            SubmitProposal proposal =
                    proposalRepository
                            .findProposalByStudentId(studentId)
                            .orElse(null);

            if (proposal != null) {

                response.setProjectTitle(
                        proposal.getProjectTitle()
                );

                response.setGuideName(
                        proposal.getGuideName()
                );
            }
        }

        // =====================================================
        // MARKS
        // =====================================================

        response.setProposalMarks(
                marks.getProposalMarks()
        );

        response.setWeeklyProgressMarks(
                marks.getWeeklyProgressMarks()
        );

        response.setFinalReportMarks(
                marks.getFinalReportMarks()
        );

        response.setPresentationVivaMarks(
                marks.getPresentationVivaMarks()
        );

        response.setSourceCodeMarks(
                marks.getSourceCodeMarks()
        );

        // =====================================================
        // TOTAL
        // =====================================================

        response.setTotalMarks(
                marks.getTotalMarks()
        );

        // =====================================================
        // PERCENTAGE
        // =====================================================

        response.setPercentage(
                marks.getPercentage()
        );

        // =====================================================
        // GRADE
        // =====================================================

        response.setGrade(
                marks.getGrade()
        );

        // =====================================================
        // RESULT
        // =====================================================

        response.setResult(
                marks.getResult()
        );

        // =====================================================
        // FACULTY COMMENTS
        // =====================================================

        response.setOverallComments(
                marks.getOverallComments()
        );

        return response;
    }
}