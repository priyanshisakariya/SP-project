package com.campus.campus_backend.repository;

import com.campus.campus_backend.entity.ReportCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportCardRepository
        extends JpaRepository<ReportCard, Integer> {

    Optional<ReportCard> findByMarksId(Integer marksId);

    Optional<ReportCard> findByStudentId(Integer studentId);
}