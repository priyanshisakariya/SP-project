package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.marks.MarksResponseDTO;
import com.campus.campus_backend.dto.reportcard.ReportCardRequestDTO;
import com.campus.campus_backend.dto.reportcard.ReportCardResponseDTO;
import com.campus.campus_backend.entity.Marks;
import com.campus.campus_backend.entity.ReportCard;
import com.campus.campus_backend.repository.MarksRepository;
import com.campus.campus_backend.repository.ReportCardRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportCardService {

    private final ReportCardRepository reportCardRepository;
    private final MarksRepository marksRepository;

    public ReportCardService(
            ReportCardRepository reportCardRepository,
            MarksRepository marksRepository) {

        this.reportCardRepository = reportCardRepository;
        this.marksRepository = marksRepository;
    }


    // GENERATE REPORT CARD
    public ReportCardResponseDTO generateReportCard(
            ReportCardRequestDTO request) {

        Marks marks = marksRepository.findById(
                request.getMarksId()
        ).orElseThrow(() ->
                new RuntimeException(
                        "Marks not found with id: "
                                + request.getMarksId()
                )
        );

        // Ensure student matches marks
        if (!marks.getStudentId().equals(request.getStudentId())) {
            throw new RuntimeException(
                    "Student ID does not match the marks record"
            );
        }

        // Prevent duplicate report card
        if (reportCardRepository
                .findByMarksId(request.getMarksId())
                .isPresent()) {

            throw new RuntimeException(
                    "Report card already exists for marks id: "
                            + request.getMarksId()
            );
        }

        ReportCard reportCard = new ReportCard();

        reportCard.setStudentId(request.getStudentId());
        reportCard.setMarksId(request.getMarksId());

        ReportCard saved =
                reportCardRepository.save(reportCard);

        return convertToResponse(saved);
    }


    // GET REPORT CARD
    public ReportCardResponseDTO getReportCard(
            Integer reportId) {

        ReportCard reportCard =
                reportCardRepository.findById(reportId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Report card not found with id: "
                                                + reportId
                                )
                        );

        return convertToResponse(reportCard);
    }


    // GET REPORT CARD BY STUDENT
    public ReportCardResponseDTO getReportCardByStudent(
            Integer studentId) {

        ReportCard reportCard =
                reportCardRepository
                        .findByStudentId(studentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Report card not found for student id: "
                                                + studentId
                                )
                        );

        return convertToResponse(reportCard);
    }


    // DELETE
    public void deleteReportCard(Integer reportId) {

        if (!reportCardRepository.existsById(reportId)) {
            throw new RuntimeException(
                    "Report card not found with id: "
                            + reportId
            );
        }

        reportCardRepository.deleteById(reportId);
    }


    private ReportCardResponseDTO convertToResponse(
            ReportCard reportCard) {

        ReportCardResponseDTO response =
                new ReportCardResponseDTO();

        response.setReportId(
                reportCard.getReportId()
        );

        response.setStudentId(
                reportCard.getStudentId()
        );

        response.setMarksId(
                reportCard.getMarksId()
        );

        return response;
    }
}