package com.campus.campus_backend.controller;

import com.campus.campus_backend.dto.weekly.WeeklyProgressRequestDTO;
import com.campus.campus_backend.dto.weekly.WeeklyProgressResponseDTO;
import com.campus.campus_backend.service.WeeklyProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weekly-progress")
@CrossOrigin(origins = "http://localhost:5173")
public class WeeklyProgressController {

    @Autowired
    private WeeklyProgressService weeklyProgressService;


    // =====================================================
    // STUDENT - SUBMIT WEEKLY PROGRESS
    // =====================================================

    @PostMapping("/submit")
    public ResponseEntity<WeeklyProgressResponseDTO> submitProgress(
            @RequestBody WeeklyProgressRequestDTO requestDTO) {

        WeeklyProgressResponseDTO response =
                weeklyProgressService.submitProgress(requestDTO);

        return ResponseEntity.ok(response);
    }


    // =====================================================
    // FACULTY - GET ALL WEEKLY PROGRESS
    // =====================================================

    @GetMapping
    public ResponseEntity<List<WeeklyProgressResponseDTO>> getAllProgress() {

        return ResponseEntity.ok(
                weeklyProgressService.getAllProgress()
        );
    }

    @PutMapping("/{progressId}/status")
    public ResponseEntity<WeeklyProgressResponseDTO> updateStatus(
            @PathVariable Integer progressId,
            @RequestBody Map<String, String> request) {

        String status = request.get("status");

        return ResponseEntity.ok(
                weeklyProgressService.updateStatus(
                        progressId,
                        status
                )
        );
    }
}