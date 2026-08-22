package com.campus.campus_backend.dto.reportcard;

public class ReportCardRequestDTO {

    private Integer studentId;
    private Integer marksId;


    public ReportCardRequestDTO() {
    }


    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getMarksId() {
        return marksId;
    }

    public void setMarksId(Integer marksId) {
        this.marksId = marksId;
    }
}