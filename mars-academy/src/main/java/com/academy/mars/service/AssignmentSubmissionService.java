package com.academy.mars.service;


import com.academy.mars.entity.*;
import com.academy.mars.repository.AssignmentRepository;
import com.academy.mars.repository.AssignmentSubmissionRepository;
import com.academy.mars.repository.CoursesRepository;
import com.academy.mars.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentSubmissionService {
    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    public AssignmentSubmission submitAssignment(Long assignmentId, Long studentId, String fileLink, Long courseId) {
        if (fileLink == null || fileLink.isEmpty()) {
            throw new RuntimeException("File link cannot be null or empty");
        }
        if (!fileLink.startsWith("http://") && !fileLink.startsWith("https://")) {
            throw new RuntimeException("Invalid file link. It must be a valid URL starting with http:// or https://");
        }
        Courses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + courseId));
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id " + assignmentId));
        AssignmentSubmission submission = new AssignmentSubmission();
        submission.setCourse(course);
        submission.setAssignment(assignment);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            submission.setStudent(student);
        } else {
            throw new RuntimeException("Student not found with id " + studentId);
        }
        submission.setFileLink(fileLink);
        return assignmentSubmissionRepository.save(submission);
    }



    public List<AssignmentSubmission> getSubmissionsByStudent(Long studentId) {
        return assignmentSubmissionRepository.findByStudentId(studentId);
    }

    public List<AssignmentSubmission> getSubmissionsByStudentAndCourse(Long studentId, Long courseId) {
        return assignmentSubmissionRepository.findByStudentIdAndCourseId(studentId, courseId);
    }
}
