package com.academy.mars.instructor;


import com.academy.mars.user.User;
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
