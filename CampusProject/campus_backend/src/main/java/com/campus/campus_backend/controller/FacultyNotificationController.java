package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.notification.FacultyNotificationRequestDTO;
import com.campus.campus_backend.dto.notification.FacultyNotificationResponseDTO;
import com.campus.campus_backend.service.FacultyNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:5173")
public class FacultyNotificationController {

    private final FacultyNotificationService service;

    public FacultyNotificationController(
            FacultyNotificationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FacultyNotificationResponseDTO>
    createNotification(
            @RequestBody FacultyNotificationRequestDTO request) {

        return ResponseEntity.ok(
                service.createNotification(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<FacultyNotificationResponseDTO>>
    getAllNotifications() {

        return ResponseEntity.ok(
                service.getAllNotifications()
        );
    }
}