package com.academy.mars.service;

import com.academy.mars.entity.Student;
import com.academy.mars.entity.User;
import com.academy.mars.repository.StudentRepository;

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
