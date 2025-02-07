package com.academy.mars.assignmentSubmission;

import com.academy.mars.assignment.Assignment;
import com.academy.mars.course.Courses;
import com.academy.mars.student.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String fileLink;


    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private LocalDateTime submissionTime;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;
}