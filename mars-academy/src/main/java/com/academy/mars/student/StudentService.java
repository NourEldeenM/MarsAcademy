package com.academy.mars.student;

import com.academy.mars.user.User;
import org.springframework.stereotype.Service;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void registerStudent(User user) {
        Student student = new Student();
        student.setUser(user);
        studentRepository.save(student);
    }
}
