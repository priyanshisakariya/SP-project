package com.campus.campus_backend.repository;

import com.campus.campus_backend.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Integer> {

//    Optional<Marks> findByStudent_Id(Integer studentId);
@Query("SELECT m FROM Marks m WHERE m.student.id = :studentId")
Optional<Marks> findMarksByStudentId(
        @Param("studentId") Integer studentId
);

}