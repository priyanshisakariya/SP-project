package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.studentmarks.StudentMarksResponseDTO;
import com.campus.campus_backend.service.StudentMarksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/marks")
@CrossOrigin(origins = "*")
public class StudentMarksController {

    private final StudentMarksService studentMarksService;

    public StudentMarksController(
            StudentMarksService studentMarksService) {

        this.studentMarksService = studentMarksService;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentMarksResponseDTO> getStudentMarks(
            @PathVariable Integer studentId) {

        return ResponseEntity.ok(
                studentMarksService.getStudentMarks(studentId)
        );
    }
}