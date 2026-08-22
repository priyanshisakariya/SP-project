package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.marks.MarksRequestDTO;
import com.campus.campus_backend.dto.marks.MarksResponseDTO;
import com.campus.campus_backend.entity.Marks;
import com.campus.campus_backend.repository.MarksRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarksService {

    private final MarksRepository marksRepository;

    public MarksService(MarksRepository marksRepository) {
        this.marksRepository = marksRepository;
    }


    // CREATE MARKS
    public MarksResponseDTO createMarks(MarksRequestDTO request) {

        validateMarks(request);

        Marks marks = new Marks();

        marks.setStudentId(request.getStudentId());
        marks.setSubmissionId(request.getSubmissionId());

        marks.setProposalMarks(request.getProposalMarks());
        marks.setWeeklyProgressMarks(request.getWeeklyProgressMarks());
        marks.setFinalReportMarks(request.getFinalReportMarks());
        marks.setPresentationVivaMarks(request.getPresentationVivaMarks());
        marks.setSourceCodeMarks(request.getSourceCodeMarks());

        marks.setStrengths(request.getStrengths());
        marks.setAreasForImprovement(
                request.getAreasForImprovement()
        );
        marks.setOverallComments(
                request.getOverallComments()
        );

        calculateResult(marks);

        Marks saved = marksRepository.save(marks);

        return convertToResponse(saved);
    }


    // GET MARKS BY ID
    public MarksResponseDTO getMarksById(Integer marksId) {

        Marks marks = marksRepository.findById(marksId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Marks not found with id: " + marksId
                        )
                );

        return convertToResponse(marks);
    }


    // GET MARKS BY STUDENT
    public List<MarksResponseDTO> getMarksByStudent(
            Integer studentId) {

        return marksRepository.findByStudentId(studentId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // UPDATE MARKS
    public MarksResponseDTO updateMarks(
            Integer marksId,
            MarksRequestDTO request) {

        validateMarks(request);

        Marks marks = marksRepository.findById(marksId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Marks not found with id: " + marksId
                        )
                );

        marks.setStudentId(request.getStudentId());
        marks.setSubmissionId(request.getSubmissionId());

        marks.setProposalMarks(request.getProposalMarks());
        marks.setWeeklyProgressMarks(
                request.getWeeklyProgressMarks()
        );
        marks.setFinalReportMarks(
                request.getFinalReportMarks()
        );
        marks.setPresentationVivaMarks(
                request.getPresentationVivaMarks()
        );
        marks.setSourceCodeMarks(
                request.getSourceCodeMarks()
        );

        marks.setStrengths(request.getStrengths());
        marks.setAreasForImprovement(
                request.getAreasForImprovement()
        );
        marks.setOverallComments(
                request.getOverallComments()
        );

        calculateResult(marks);

        Marks updated = marksRepository.save(marks);

        return convertToResponse(updated);
    }


    // DELETE
    public void deleteMarks(Integer marksId) {

        if (!marksRepository.existsById(marksId)) {
            throw new RuntimeException(
                    "Marks not found with id: " + marksId
            );
        }

        marksRepository.deleteById(marksId);
    }


    // CALCULATE TOTAL / PERCENTAGE / GRADE / RESULT
    private void calculateResult(Marks marks) {

        int total =
                marks.getProposalMarks()
                        + marks.getWeeklyProgressMarks()
                        + marks.getFinalReportMarks()
                        + marks.getPresentationVivaMarks()
                        + marks.getSourceCodeMarks();

        marks.setTotalMarks(total);

        BigDecimal percentage =
                BigDecimal.valueOf(total)
                        .divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP
                        )
                        .multiply(BigDecimal.valueOf(100));

        marks.setPercentage(percentage);

        String grade;

        if (total >= 85) {
            grade = "A+";
        } else if (total >= 75) {
            grade = "A";
        } else if (total >= 65) {
            grade = "B+";
        } else if (total >= 50) {
            grade = "B";
        } else {
            grade = "C";
        }

        marks.setGrade(grade);

        if (total >= 40) {
            marks.setResult("PASS");
        } else {
            marks.setResult("FAIL");
        }
    }


    // VALIDATION
    private void validateMarks(MarksRequestDTO request) {

        if (request.getStudentId() == null) {
            throw new RuntimeException("Student ID is required");
        }

        validateRange(
                "Proposal marks",
                request.getProposalMarks(),
                0,
                20
        );

        validateRange(
                "Weekly progress marks",
                request.getWeeklyProgressMarks(),
                0,
                30
        );

        validateRange(
                "Final report marks",
                request.getFinalReportMarks(),
                0,
                20
        );

        validateRange(
                "Presentation/Viva marks",
                request.getPresentationVivaMarks(),
                0,
                20
        );

        validateRange(
                "Source code marks",
                request.getSourceCodeMarks(),
                0,
                10
        );
    }


    private void validateRange(
            String field,
            Integer value,
            int min,
            int max) {

        if (value == null) {
            throw new RuntimeException(
                    field + " are required"
            );
        }

        if (value < min || value > max) {
            throw new RuntimeException(
                    field + " must be between "
                            + min + " and " + max
            );
        }
    }


    // ENTITY → RESPONSE DTO
    private MarksResponseDTO convertToResponse(Marks marks) {

        MarksResponseDTO response =
                new MarksResponseDTO();

        response.setMarksId(marks.getMarksId());
        response.setStudentId(marks.getStudentId());
        response.setSubmissionId(marks.getSubmissionId());

        response.setProposalMarks(marks.getProposalMarks());
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

        response.setTotalMarks(marks.getTotalMarks());
        response.setPercentage(marks.getPercentage());
        response.setGrade(marks.getGrade());
        response.setResult(marks.getResult());

        response.setStrengths(marks.getStrengths());
        response.setAreasForImprovement(
                marks.getAreasForImprovement()
        );
        response.setOverallComments(
                marks.getOverallComments()
        );

        return response;
    }
}