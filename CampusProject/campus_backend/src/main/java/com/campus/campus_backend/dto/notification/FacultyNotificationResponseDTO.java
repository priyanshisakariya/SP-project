package com.campus.campus_backend.dto.notification;

public class FacultyNotificationResponseDTO {

    private Long id;
    private String title;
    private String message;
    private String date;

    public FacultyNotificationResponseDTO() {
    }

    public FacultyNotificationResponseDTO(
            Long id,
            String title,
            String message,
            String date) {

        this.id = id;
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
