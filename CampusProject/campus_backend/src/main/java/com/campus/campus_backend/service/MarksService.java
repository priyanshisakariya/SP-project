package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.marks.MarksRequestDTO;
import com.campus.campus_backend.dto.marks.MarksResponseDTO;
import com.campus.campus_backend.dto.marks.MarksStudentResponseDTO;

import java.util.List;

public interface MarksService {

    List<MarksStudentResponseDTO> getStudentsForMarks();

    MarksResponseDTO getMarksByStudentId(Integer studentId);

    MarksResponseDTO saveMarks(MarksRequestDTO requestDTO);
}