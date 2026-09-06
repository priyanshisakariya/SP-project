package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.marks.MarksRequestDTO;
import com.campus.campus_backend.dto.marks.MarksResponseDTO;
import com.campus.campus_backend.dto.marks.MarksStudentResponseDTO;
import com.campus.campus_backend.service.MarksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marks")
@CrossOrigin(origins = "http://localhost:5173")
public class MarksController {

    private final MarksService marksService;


    public MarksController(MarksService marksService) {
        this.marksService = marksService;
    }


    // Get all students for Mark Management table
    @GetMapping("/students")
    public ResponseEntity<List<MarksStudentResponseDTO>> getStudentsForMarks() {

        return ResponseEntity.ok(
                marksService.getStudentsForMarks()
        );
    }


    // Get marks of one student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getMarksByStudent(
            @PathVariable Integer studentId
    ) {

        MarksResponseDTO response =
                marksService.getMarksByStudentId(studentId);

        if (response == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Marks not assigned for this student.");
        }

        return ResponseEntity.ok(response);
    }


    // Save or update marks
    @PostMapping
    public ResponseEntity<MarksResponseDTO> saveMarks(
            @RequestBody MarksRequestDTO requestDTO
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(marksService.saveMarks(requestDTO));
    }
}