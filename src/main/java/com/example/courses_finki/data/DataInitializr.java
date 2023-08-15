package com.example.courses_finki.data;

import com.example.courses_finki.entity.semester.SemesterEntity;
import com.example.courses_finki.entity.semester.SemesterType;
import com.example.courses_finki.entity.subject.SubjectEntity;
import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.entity.user.UserRole;
import com.example.courses_finki.repository.semester.SemesterRepository;
import com.example.courses_finki.repository.subject.SubjectRepository;
import com.example.courses_finki.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializr {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;
    private final PasswordEncoder passwordEncoder;


    public DataInitializr(UserRepository userRepository, SubjectRepository subjectRepository, SemesterRepository semesterRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeData() {
        UserEntity user1 = new UserEntity("admin", "admin", "admin", passwordEncoder.encode("admin"), UserRole.ADMIN);
        UserEntity user2 = new UserEntity("Student1", "Surname1", "222222", passwordEncoder.encode("pw2"), UserRole.STUDENT);
        UserEntity user3 = new UserEntity("Student2", "Surname2", "333333", passwordEncoder.encode("pw3"), UserRole.STUDENT);
        UserEntity user4 = new UserEntity("Professor1", "Professor Surname1", "prof1", passwordEncoder.encode("prof1"), UserRole.PROFESSOR);
        UserEntity user5 = new UserEntity("Professor2", "Professor Surname2", "prof2", passwordEncoder.encode("prof2"), UserRole.PROFESSOR);

        userRepository.save(user1);

        userRepository.save(user4);
        userRepository.save(user5);

        SemesterEntity semester1 = new SemesterEntity(SemesterType.WINTER, 2023);
        SemesterEntity semester2 = new SemesterEntity(SemesterType.SUMMER, 2023);

        semesterRepository.save(semester1);
        semesterRepository.save(semester2);


        SubjectEntity subject1 = new SubjectEntity("Operating Systems - 2022/2023");
        subject1.setProfessors(List.of(user4));
        subject1.setSemester(semester2);
        SubjectEntity subject2 = new SubjectEntity("Web Programming - 2022/2023");
        subject2.setProfessors(List.of(user5));
        subject2.setSemester(semester1);

        subjectRepository.save(subject1);
        subjectRepository.save(subject2);

        user2.setSubjects(List.of(subject1));
        user3.setSubjects(List.of(subject2));
        user4.setSubjects(List.of(subject1));
        user5.setSubjects(List.of(subject2));

        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
    }
}
