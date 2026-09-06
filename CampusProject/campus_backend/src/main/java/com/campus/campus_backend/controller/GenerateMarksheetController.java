package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.generatemarksheet.GenerateMarksheetResponseDTO;
import com.campus.campus_backend.service.GenerateMarksheetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generate-marksheet")
@CrossOrigin(origins = "*")
public class GenerateMarksheetController {

    private final GenerateMarksheetService service;

    public GenerateMarksheetController(
            GenerateMarksheetService service
    ) {
        this.service = service;
    }

    // =========================================================
    // GET STUDENTS WHO HAVE MARKS
    // =========================================================

    @GetMapping("/students")
    public ResponseEntity<List<GenerateMarksheetResponseDTO>>
    getStudents() {

        return ResponseEntity.ok(
                service.getAllStudents()
        );
    }

    // =========================================================
    // GET MARKSHEET FOR ONE STUDENT
    // =========================================================

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentMarksheet(
            @PathVariable Integer studentId
    ) {

        try {

            return ResponseEntity.ok(
                    service.getStudentMarksheet(studentId)
            );

        } catch (RuntimeException exception) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            exception.getMessage()
                    );
        }
    }
}