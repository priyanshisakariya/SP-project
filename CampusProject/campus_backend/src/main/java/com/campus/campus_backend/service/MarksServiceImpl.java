package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.marks.MarksRequestDTO;
import com.campus.campus_backend.dto.marks.MarksResponseDTO;
import com.campus.campus_backend.dto.marks.MarksStudentResponseDTO;
import com.campus.campus_backend.entity.Marks;
import com.campus.campus_backend.entity.Student;
import com.campus.campus_backend.entity.SubmitProposal;
import com.campus.campus_backend.repository.MarksRepository;
import com.campus.campus_backend.repository.StudentRepository;
import com.campus.campus_backend.repository.SubmitProposalRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarksServiceImpl implements MarksService {

    private final MarksRepository marksRepository;
    private final StudentRepository studentRepository;
    private final SubmitProposalRepository submitProposalRepository;


    public MarksServiceImpl(
            MarksRepository marksRepository,
            StudentRepository studentRepository,
            SubmitProposalRepository submitProposalRepository
    ) {
        this.marksRepository = marksRepository;
        this.studentRepository = studentRepository;
        this.submitProposalRepository = submitProposalRepository;
    }


    @Override
    public List<MarksStudentResponseDTO> getStudentsForMarks() {

        List<Student> students = studentRepository.findAll();

        List<MarksStudentResponseDTO> responseList = new ArrayList<>();

        for (Student student : students) {

            MarksStudentResponseDTO dto = new MarksStudentResponseDTO();

            dto.setStudentId(student.getId());
            dto.setName(student.getFullName());
            dto.setEnrollment(student.getEnrollmentNo());

            SubmitProposal proposal =
                    submitProposalRepository.findByStudent(student).orElse(null);

            if (proposal != null) {
                dto.setProject(proposal.getProjectTitle());
            } else {
                dto.setProject("Project Not Submitted");
            }


            Marks marks =
                    marksRepository.findMarksByStudentId(student.getId()).orElse(null);

            if (marks != null) {

                dto.setMarksId(marks.getMarksId());
                dto.setTotalMarks(marks.getTotalMarks());
                dto.setGrade(marks.getGrade());
                dto.setResult(marks.getResult());

            } else {

                dto.setMarksId(null);
                dto.setTotalMarks(0);
                dto.setGrade("-");
                dto.setResult("Pending");
            }

            responseList.add(dto);
        }

        return responseList;
    }


    @Override
    public MarksResponseDTO getMarksByStudentId(Integer studentId) {

        Marks marks = marksRepository.findMarksByStudentId(studentId)
                .orElse(null);

        if (marks == null) {
            return null;
        }

        return convertToResponse(marks);
    }


    @Override
    public MarksResponseDTO saveMarks(MarksRequestDTO requestDTO) {

        if (requestDTO.getStudentId() == null) {
            throw new RuntimeException("Student ID is required.");
        }


        Student student = studentRepository
                .findById(requestDTO.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found.")
                );


        int proposalMarks = valueOrZero(requestDTO.getProposalMarks());
        int weeklyMarks = valueOrZero(requestDTO.getWeeklyProgressMarks());
        int finalReportMarks = valueOrZero(requestDTO.getFinalReportMarks());
        int presentationVivaMarks =
                valueOrZero(requestDTO.getPresentationVivaMarks());
        int sourceCodeMarks =
                valueOrZero(requestDTO.getSourceCodeMarks());


        validateMarks("Proposal", proposalMarks, 20);
        validateMarks("Weekly Review", weeklyMarks, 30);
        validateMarks("Final Report", finalReportMarks, 10);
        validateMarks("Presentation + Viva", presentationVivaMarks, 20);
        validateMarks("Source Code / Implementation", sourceCodeMarks, 20);


        int totalMarks =
                proposalMarks
                        + weeklyMarks
                        + finalReportMarks
                        + presentationVivaMarks
                        + sourceCodeMarks;


        BigDecimal percentage =
                BigDecimal.valueOf(totalMarks)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);


        String grade = calculateGrade(totalMarks);
        String result = totalMarks >= 40 ? "PASS" : "FAIL";


        Marks marks =
                marksRepository.findMarksByStudentId(student.getId())
                        .orElse(new Marks());


        marks.setStudent(student);

        /*
         * submissionId is accepted from the frontend/database.
         *
         * Your current backend ZIP does not contain a FinalSubmission entity,
         * so we do not automatically create a fake submission ID here.
         */
        marks.setProposalId(requestDTO.getProposalId());

        marks.setProposalMarks(proposalMarks);
        marks.setWeeklyProgressMarks(weeklyMarks);
        marks.setFinalReportMarks(finalReportMarks);
        marks.setPresentationVivaMarks(presentationVivaMarks);
        marks.setSourceCodeMarks(sourceCodeMarks);

        marks.setTotalMarks(totalMarks);
        marks.setPercentage(percentage);
        marks.setGrade(grade);
        marks.setResult(result);

        marks.setStrengths(requestDTO.getStrengths());
        marks.setAreasForImprovement(requestDTO.getAreasForImprovement());
        marks.setOverallComments(requestDTO.getOverallComments());


        Marks savedMarks = marksRepository.save(marks);

        return convertToResponse(savedMarks);
    }


    private int valueOrZero(Integer value) {

        return value == null ? 0 : value;
    }


    private void validateMarks(
            String field,
            int marks,
            int maximum
    ) {

        if (marks < 0 || marks > maximum) {

            throw new RuntimeException(
                    field + " marks must be between 0 and " + maximum
            );
        }
    }


    private String calculateGrade(int totalMarks) {

        if (totalMarks >= 90) {
            return "A+";
        } else if (totalMarks >= 80) {
            return "A";
        } else if (totalMarks >= 70) {
            return "B+";
        } else if (totalMarks >= 60) {
            return "B";
        } else if (totalMarks >= 50) {
            return "C";
        } else if (totalMarks >= 40) {
            return "D";
        } else {
            return "F";
        }
    }


    private MarksResponseDTO convertToResponse(Marks marks) {

        MarksResponseDTO dto = new MarksResponseDTO();

        dto.setMarksId(marks.getMarksId());

        if (marks.getStudent() != null) {
            dto.setStudentId(marks.getStudent().getId());
        }

        dto.setProposalId(marks.getProposalId());

        dto.setProposalMarks(marks.getProposalMarks());
        dto.setWeeklyProgressMarks(marks.getWeeklyProgressMarks());
        dto.setFinalReportMarks(marks.getFinalReportMarks());
        dto.setPresentationVivaMarks(marks.getPresentationVivaMarks());
        dto.setSourceCodeMarks(marks.getSourceCodeMarks());

        dto.setTotalMarks(marks.getTotalMarks());
        dto.setPercentage(marks.getPercentage());
        dto.setGrade(marks.getGrade());
        dto.setResult(marks.getResult());

        dto.setStrengths(marks.getStrengths());
        dto.setAreasForImprovement(marks.getAreasForImprovement());
        dto.setOverallComments(marks.getOverallComments());

        return dto;
    }
}