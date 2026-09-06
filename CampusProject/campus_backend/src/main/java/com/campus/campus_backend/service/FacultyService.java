package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.Faculty.FacultyRegistrationRequest;
import com.campus.campus_backend.dto.Faculty.FacultyLoginRequest;
import com.campus.campus_backend.dto.Faculty.FacultyResponse;
import com.campus.campus_backend.entity.Faculty;
import com.campus.campus_backend.repository.FacultyRepository;
import org.springframework.stereotype.Service;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    // ============================
    // FACULTY REGISTRATION
    // ============================

    public FacultyResponse registerFaculty(
            FacultyRegistrationRequest request
    ) {

        if (facultyRepository.existsByPhoneNumber(
                request.getPhoneNumber()
        )) {

            throw new RuntimeException(
                    "Faculty with this phone number already exists"
            );
        }

        Faculty faculty = new Faculty();

        faculty.setFacultyName(
                request.getFacultyName()
        );

        faculty.setPhoneNumber(
                request.getPhoneNumber()
        );

        faculty.setDesignation(
                request.getDesignation()
        );

        faculty.setDepartment(
                request.getDepartment()
        );

        // TEMPORARY: store phone number as password
        faculty.setPassword(
                request.getPhoneNumber()
        );

        Faculty savedFaculty =
                facultyRepository.save(faculty);

        return new FacultyResponse(savedFaculty);
    }


    // ============================
    // FACULTY LOGIN
    // ============================

    public FacultyResponse loginFaculty(
            FacultyLoginRequest request
    ) {

        Faculty faculty =
                facultyRepository
                        .findByPhoneNumber(
                                request.getPhoneNumber()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Faculty not found"
                                )
                        );

        // TEMPORARY: direct password comparison
        boolean passwordMatches =
                request.getPassword()
                        .equals(faculty.getPassword());

        if (!passwordMatches) {

            throw new RuntimeException(
                    "Invalid phone number or password"
            );
        }

        return new FacultyResponse(faculty);
    }
}