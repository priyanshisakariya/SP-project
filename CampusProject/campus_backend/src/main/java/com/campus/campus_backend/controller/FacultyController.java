package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.Faculty.FacultyLoginRequest;
import com.campus.campus_backend.dto.Faculty.FacultyRegistrationRequest;
import com.campus.campus_backend.dto.Faculty.FacultyResponse;
import com.campus.campus_backend.service.FacultyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    private final FacultyService facultyService;


    public FacultyController(
            FacultyService facultyService
    ) {
        this.facultyService = facultyService;
    }


    // ============================
    // FACULTY REGISTRATION
    // ============================

    @PostMapping("/register")
    public ResponseEntity<?> registerFaculty(
            @Valid @RequestBody FacultyRegistrationRequest request
    ) {

        try {

            FacultyResponse response =
                    facultyService.registerFaculty(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // ============================
    // FACULTY LOGIN
    // ============================

    @PostMapping("/login")
    public ResponseEntity<?> loginFaculty(
            @Valid @RequestBody FacultyLoginRequest request
    ) {

        try {

            FacultyResponse response =
                    facultyService.loginFaculty(request);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }
}