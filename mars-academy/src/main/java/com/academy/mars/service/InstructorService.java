package com.academy.mars.service;

import com.academy.mars.entity.Instructor;
import com.academy.mars.entity.InstructorSpecialization;
import com.academy.mars.entity.User;
import com.academy.mars.repository.InstructorRepository;


import org.springframework.stereotype.Service;


@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public void registerInstructor(User user) {
        Instructor instructor = new Instructor();
        instructor.setUser(user);
        instructor.setSpecialization(InstructorSpecialization.CS);
        instructorRepository.save(instructor);
    }
}
