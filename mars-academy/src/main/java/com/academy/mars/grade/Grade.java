package com.academy.mars.grade;


import com.academy.mars.assignment.Assignment;
import com.academy.mars.course.Courses;
import com.academy.mars.instructor.Instructor;
import com.academy.mars.quiz.Quiz;
import com.academy.mars.student.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    private int score;
    private String feedback;
    private boolean isManualFeedback;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;


    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
