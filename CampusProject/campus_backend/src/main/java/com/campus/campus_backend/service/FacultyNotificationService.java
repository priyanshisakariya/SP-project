package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.notification.FacultyNotificationRequestDTO;
import com.campus.campus_backend.dto.notification.FacultyNotificationResponseDTO;
import com.campus.campus_backend.entity.FacultyNotification;
import com.campus.campus_backend.repository.FacultyNotificationRepository;

import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FacultyNotificationService {

    private final FacultyNotificationRepository repository;

    public FacultyNotificationService(
            FacultyNotificationRepository repository) {
        this.repository = repository;
    }

    public FacultyNotificationResponseDTO createNotification(
            FacultyNotificationRequestDTO request) {

        FacultyNotification notification =
                new FacultyNotification();

        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());

        notification.setCreatedAt(
                java.time.LocalDateTime.now()
        );

        FacultyNotification saved =
                repository.save(notification);

        return convertToResponse(saved);
    }

    public List<FacultyNotificationResponseDTO> getAllNotifications() {

        return repository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private FacultyNotificationResponseDTO convertToResponse(
            FacultyNotification notification) {

        String formattedDate =
                notification.getCreatedAt()
                        .format(
                                DateTimeFormatter.ofPattern("dd MMM yyyy")
                        );

        return new FacultyNotificationResponseDTO(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                formattedDate
        );
    }
}