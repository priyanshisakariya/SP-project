package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.reportcard.ReportCardRequestDTO;
import com.campus.campus_backend.dto.reportcard.ReportCardResponseDTO;
import com.campus.campus_backend.service.ReportCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report-card")
@CrossOrigin(origins = "*")
public class ReportCardController {

    private final ReportCardService reportCardService;

    public ReportCardController(
            ReportCardService reportCardService) {

        this.reportCardService = reportCardService;
    }


    // GENERATE MARKSHEET
    @PostMapping
    public ResponseEntity<ReportCardResponseDTO> generateReportCard(
            @RequestBody ReportCardRequestDTO request) {

        return new ResponseEntity<>(
                reportCardService.generateReportCard(request),
                HttpStatus.CREATED
        );
    }


    // GET REPORT CARD
    @GetMapping("/{reportId}")
    public ResponseEntity<ReportCardResponseDTO> getReportCard(
            @PathVariable Integer reportId) {

        return ResponseEntity.ok(
                reportCardService.getReportCard(reportId)
        );
    }


    // GET REPORT CARD BY STUDENT
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ReportCardResponseDTO>
    getReportCardByStudent(
            @PathVariable Integer studentId) {

        return ResponseEntity.ok(
                reportCardService.getReportCardByStudent(
                        studentId
                )
        );
    }


    // DELETE
    @DeleteMapping("/{reportId}")
    public ResponseEntity<String> deleteReportCard(
            @PathVariable Integer reportId) {

        reportCardService.deleteReportCard(reportId);

        return ResponseEntity.ok(
                "Report card deleted successfully"
        );
    }
}