package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.marks.MarksRequestDTO;
import com.campus.campus_backend.dto.marks.MarksResponseDTO;
import com.campus.campus_backend.service.MarksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
@CrossOrigin(origins = "*")
public class MarksController {

    private final MarksService marksService;

    public MarksController(MarksService marksService) {
        this.marksService = marksService;
    }


    // SAVE MARKS
    @PostMapping
    public ResponseEntity<MarksResponseDTO> createMarks(
            @RequestBody MarksRequestDTO request) {

        return new ResponseEntity<>(
                marksService.createMarks(request),
                HttpStatus.CREATED
        );
    }


    // GET MARKS
    @GetMapping("/{marksId}")
    public ResponseEntity<MarksResponseDTO> getMarksById(
            @PathVariable Integer marksId) {

        return ResponseEntity.ok(
                marksService.getMarksById(marksId)
        );
    }


    // GET MARKS BY STUDENT
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<MarksResponseDTO>> getMarksByStudent(
            @PathVariable Integer studentId) {

        return ResponseEntity.ok(
                marksService.getMarksByStudent(studentId)
        );
    }


    // UPDATE MARKS
    @PutMapping("/{marksId}")
    public ResponseEntity<MarksResponseDTO> updateMarks(
            @PathVariable Integer marksId,
            @RequestBody MarksRequestDTO request) {

        return ResponseEntity.ok(
                marksService.updateMarks(
                        marksId,
                        request
                )
        );
    }


    // DELETE MARKS
    @DeleteMapping("/{marksId}")
    public ResponseEntity<String> deleteMarks(
            @PathVariable Integer marksId) {

        marksService.deleteMarks(marksId);

        return ResponseEntity.ok(
                "Marks deleted successfully"
        );
    }
}