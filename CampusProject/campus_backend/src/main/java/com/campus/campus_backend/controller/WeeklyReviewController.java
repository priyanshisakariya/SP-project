package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.weeklyreview.WeeklyReviewRequestDTO;
import com.campus.campus_backend.dto.weeklyreview.WeeklyReviewResponseDTO;
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

        this.weeklyReviewService = weeklyReviewService;
    }


    // CREATE REVIEW
    @PostMapping
    public ResponseEntity<WeeklyReviewResponseDTO> createReview(
            @RequestBody WeeklyReviewRequestDTO request) {

        return new ResponseEntity<>(
                weeklyReviewService.createReview(request),
                HttpStatus.CREATED
        );
    }


    // GET REVIEW BY ID
    @GetMapping("/{weeklyReviewId}")
    public ResponseEntity<WeeklyReviewResponseDTO> getReviewById(
            @PathVariable Integer weeklyReviewId) {

        return ResponseEntity.ok(
                weeklyReviewService.getReviewById(
                        weeklyReviewId
                )
        );
    }


    // GET REVIEW BY PROGRESS ID
    @GetMapping("/progress/{progressId}")
    public ResponseEntity<WeeklyReviewResponseDTO>
    getReviewByProgressId(
            @PathVariable Integer progressId) {

        return ResponseEntity.ok(
                weeklyReviewService
                        .getReviewByProgressId(progressId)
        );
    }


    // UPDATE REVIEW
    @PutMapping("/{weeklyReviewId}")
    public ResponseEntity<WeeklyReviewResponseDTO> updateReview(
            @PathVariable Integer weeklyReviewId,
            @RequestBody WeeklyReviewRequestDTO request) {

        return ResponseEntity.ok(
                weeklyReviewService.updateReview(
                        weeklyReviewId,
                        request
                )
        );
    }


    // DELETE REVIEW
    @DeleteMapping("/{weeklyReviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Integer weeklyReviewId) {

        weeklyReviewService.deleteReview(
                weeklyReviewId
        );

        return ResponseEntity.ok(
                "Weekly review deleted successfully"
        );
    }
}