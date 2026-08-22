package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.weeklyreview.WeeklyReviewRequestDTO;
import com.campus.campus_backend.dto.weeklyreview.WeeklyReviewResponseDTO;
import com.campus.campus_backend.entity.WeeklyReview;
import com.campus.campus_backend.repository.WeeklyProgressRepository;
import com.campus.campus_backend.repository.WeeklyReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class WeeklyReviewService {

    private final WeeklyReviewRepository weeklyReviewRepository;
    private final WeeklyProgressRepository weeklyProgressRepository;


    public WeeklyReviewService(
            WeeklyReviewRepository weeklyReviewRepository,
            WeeklyProgressRepository weeklyProgressRepository) {

        this.weeklyReviewRepository = weeklyReviewRepository;
        this.weeklyProgressRepository = weeklyProgressRepository;
    }


    // CREATE REVIEW
    public WeeklyReviewResponseDTO createReview(
            WeeklyReviewRequestDTO request) {

        // Check whether progress exists
        if (!weeklyProgressRepository.existsById(request.getProgressId())) {
            throw new RuntimeException(
                    "Weekly progress not found with id: "
                            + request.getProgressId());
        }

        // Prevent duplicate review
        if (weeklyReviewRepository
                .findByProgressId(request.getProgressId())
                .isPresent()) {

            throw new RuntimeException(
                    "Review already exists for progress id: "
                            + request.getProgressId());
        }

        WeeklyReview review = new WeeklyReview();

        review.setProgressId(request.getProgressId());
        review.setFacultyFeedback(request.getFacultyFeedback());

        WeeklyReview saved =
                weeklyReviewRepository.save(review);

        return convertToResponse(saved);
    }


    // GET REVIEW BY ID
    public WeeklyReviewResponseDTO getReviewById(
            Integer weeklyReviewId) {

        WeeklyReview review =
                weeklyReviewRepository.findById(weeklyReviewId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Weekly review not found with id: "
                                                + weeklyReviewId));

        return convertToResponse(review);
    }


    // GET REVIEW BY PROGRESS ID
    public WeeklyReviewResponseDTO getReviewByProgressId(
            Integer progressId) {

        WeeklyReview review =
                weeklyReviewRepository
                        .findByProgressId(progressId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "No review found for progress id: "
                                                + progressId));

        return convertToResponse(review);
    }


    // UPDATE REVIEW
    public WeeklyReviewResponseDTO updateReview(
            Integer weeklyReviewId,
            WeeklyReviewRequestDTO request) {

        WeeklyReview review =
                weeklyReviewRepository.findById(weeklyReviewId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Weekly review not found with id: "
                                                + weeklyReviewId));

        review.setFacultyFeedback(
                request.getFacultyFeedback()
        );

        WeeklyReview updated =
                weeklyReviewRepository.save(review);

        return convertToResponse(updated);
    }


    // DELETE REVIEW
    public void deleteReview(Integer weeklyReviewId) {

        if (!weeklyReviewRepository.existsById(weeklyReviewId)) {
            throw new RuntimeException(
                    "Weekly review not found with id: "
                            + weeklyReviewId);
        }

        weeklyReviewRepository.deleteById(weeklyReviewId);
    }


    private WeeklyReviewResponseDTO convertToResponse(
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