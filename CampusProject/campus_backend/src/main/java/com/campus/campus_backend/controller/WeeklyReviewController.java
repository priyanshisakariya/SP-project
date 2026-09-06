package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.WeeklyReview.WeeklyReviewRequestDTO;
import com.campus.campus_backend.dto.WeeklyReview.WeeklyReviewResponseDTO;
import com.campus.campus_backend.service.WeeklyReviewService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weekly-review")
@CrossOrigin(origins = "*")
public class WeeklyReviewController {

    private final WeeklyReviewService weeklyReviewService;


    public WeeklyReviewController(
            WeeklyReviewService weeklyReviewService) {

        this.weeklyReviewService =
                weeklyReviewService;
    }


    // =====================================================
    // GET REVIEW BY PROGRESS ID
    // =====================================================

    @GetMapping("/progress/{progressId}")
    public ResponseEntity<?> getReviewByProgressId(
            @PathVariable Integer progressId) {

        try {

            return ResponseEntity.ok(
                    weeklyReviewService
                            .getReviewByProgressId(
                                    progressId
                            )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            java.util.Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }


    // =====================================================
    // CREATE OR UPDATE REVIEW
    // =====================================================

    @PutMapping("/progress/{progressId}")
    public ResponseEntity<?> saveReview(
            @PathVariable Integer progressId,
            @RequestBody WeeklyReviewRequestDTO request) {

        try {

            request.setProgressId(progressId);


            WeeklyReviewResponseDTO response =
                    weeklyReviewService
                            .saveReview(request);


            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            java.util.Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }
}