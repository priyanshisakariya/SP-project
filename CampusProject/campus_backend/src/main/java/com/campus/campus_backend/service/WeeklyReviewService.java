package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.WeeklyReview.WeeklyReviewRequestDTO;
import com.campus.campus_backend.dto.WeeklyReview.WeeklyReviewResponseDTO;
import com.campus.campus_backend.entity.WeeklyReview;
import com.campus.campus_backend.repository.WeeklyReviewRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeeklyReviewService {

    private final WeeklyReviewRepository weeklyReviewRepository;


    public WeeklyReviewService(
            WeeklyReviewRepository weeklyReviewRepository) {

        this.weeklyReviewRepository =
                weeklyReviewRepository;
    }


    // =====================================================
    // GET REVIEW BY PROGRESS ID
    // =====================================================

    public WeeklyReviewResponseDTO
    getReviewByProgressId(Integer progressId) {

        Optional<WeeklyReview> review =
                weeklyReviewRepository
                        .findByProgressId(progressId);


        if (review.isEmpty()) {

            throw new RuntimeException(
                    "No faculty review found for this weekly progress."
            );
        }


        return convertToResponse(
                review.get()
        );
    }


    // =====================================================
    // CREATE OR UPDATE REVIEW
    // =====================================================

    public WeeklyReviewResponseDTO
    saveReview(
            WeeklyReviewRequestDTO request) {


        if (request.getProgressId() == null) {

            throw new RuntimeException(
                    "Progress ID is required."
            );
        }


        if (request.getFacultyFeedback() == null ||
                request.getFacultyFeedback().trim().isEmpty()) {

            throw new RuntimeException(
                    "Faculty feedback is required."
            );
        }


        /*
         * Check whether a review already exists.
         *
         * If it exists:
         *      UPDATE it
         *
         * If it doesn't exist:
         *      CREATE it
         */

        WeeklyReview review =
                weeklyReviewRepository
                        .findByProgressId(
                                request.getProgressId()
                        )
                        .orElseGet(
                                WeeklyReview::new
                        );


        review.setProgressId(
                request.getProgressId()
        );


        review.setFacultyFeedback(
                request.getFacultyFeedback().trim()
        );


        WeeklyReview saved =
                weeklyReviewRepository.save(review);


        return convertToResponse(saved);
    }


    // =====================================================
    // CONVERT ENTITY → DTO
    // =====================================================

    private WeeklyReviewResponseDTO
    convertToResponse(
            WeeklyReview review) {

        WeeklyReviewResponseDTO response =
                new WeeklyReviewResponseDTO();


        response.setWeeklyReviewId(
                review.getWeeklyReviewId()
        );


        response.setProgressId(
                review.getProgressId()
        );


        response.setFacultyFeedback(
                review.getFacultyFeedback()
        );


        return response;
    }
}