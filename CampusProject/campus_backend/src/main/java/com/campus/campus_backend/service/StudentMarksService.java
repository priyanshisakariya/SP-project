package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.studentmarks.StudentMarksResponseDTO;
import com.campus.campus_backend.entity.Marks;
import com.campus.campus_backend.repository.MarksRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentMarksService {

    private final MarksRepository marksRepository;

    public StudentMarksService(MarksRepository marksRepository) {
        this.marksRepository = marksRepository;
    }

    public StudentMarksResponseDTO getStudentMarks(Integer studentId) {

        Marks marks = marksRepository
                .findMarksByStudentId(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Marks not found for student id: " + studentId
                        )
                );

        return convertToResponse(marks);
    }

    private StudentMarksResponseDTO convertToResponse(Marks marks) {

        StudentMarksResponseDTO response =
                new StudentMarksResponseDTO();

        response.setMarksId(
                marks.getMarksId()
        );

        if (marks.getStudent() != null) {
            response.setStudentId(
                    marks.getStudent().getId()
            );
        }

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

        response.setTotalMarks(
                marks.getTotalMarks()
        );

        response.setPercentage(
                marks.getPercentage()
        );

        response.setGrade(
                marks.getGrade()
        );

        response.setResult(
                marks.getResult()
        );

        response.setStrengths(
                marks.getStrengths()
        );

        response.setAreasForImprovement(
                marks.getAreasForImprovement()
        );

        response.setOverallComments(
                marks.getOverallComments()
        );

        return response;
    }
}