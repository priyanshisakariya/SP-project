package com.campus.campus_backend.service;

import com.campus.campus_backend.dto.Faculty.FacultyRegistrationRequest;
import com.campus.campus_backend.dto.Faculty.FacultyLoginRequest;
import com.campus.campus_backend.dto.Faculty.FacultyResponse;
import com.campus.campus_backend.entity.Faculty;
import com.campus.campus_backend.repository.FacultyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final PasswordEncoder passwordEncoder;


    public FacultyService(
            FacultyRepository facultyRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.facultyRepository = facultyRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // ============================
    // FACULTY REGISTRATION
    // ============================

    public FacultyResponse registerFaculty(
            FacultyRegistrationRequest request
    ) {

        // Check duplicate phone number
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


        // Password = Phone Number
        String password = request.getPhoneNumber();


        // Encrypt password using BCrypt
        String encryptedPassword =
                passwordEncoder.encode(password);


        faculty.setPassword(encryptedPassword);


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


        // Compare entered password
        // with encrypted password
        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        faculty.getPassword()
                );


        if (!passwordMatches) {

            throw new RuntimeException(
                    "Invalid phone number or password"
            );
        }


        return new FacultyResponse(faculty);
    }
}