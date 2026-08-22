package com.campus.campus_backend.dto.weeklyreview;

public class WeeklyReviewRequestDTO {

    private Integer progressId;
    private String facultyFeedback;


    public WeeklyReviewRequestDTO() {
    }


    public Integer getProgressId() {
        return progressId;
    }

    public void setProgressId(Integer progressId) {
        this.progressId = progressId;
    }

    public String getFacultyFeedback() {
        return facultyFeedback;
    }

    public void setFacultyFeedback(String facultyFeedback) {
        this.facultyFeedback = facultyFeedback;
    }
}