package com.campus.campus_backend.repository;

import com.campus.campus_backend.entity.Student;
import com.campus.campus_backend.entity.SubmitProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmitProposalRepository extends JpaRepository<SubmitProposal, Integer> {

    boolean existsByStudent(Student student);

    boolean existsByProjectTitle(String projectTitle);

    Optional<SubmitProposal> findByStudent(Student student);
}