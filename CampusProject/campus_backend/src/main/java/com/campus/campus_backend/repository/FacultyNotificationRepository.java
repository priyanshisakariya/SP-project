package com.campus.campus_backend.repository;


import com.campus.campus_backend.entity.FacultyNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyNotificationRepository
        extends JpaRepository<FacultyNotification, Long> {
}