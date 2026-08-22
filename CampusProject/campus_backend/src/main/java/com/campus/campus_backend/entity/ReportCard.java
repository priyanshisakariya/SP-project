package com.campus.campus_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "report_card")
public class ReportCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Integer reportId;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "marks_id", nullable = false)
    private Integer marksId;


    public ReportCard() {
    }


    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
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