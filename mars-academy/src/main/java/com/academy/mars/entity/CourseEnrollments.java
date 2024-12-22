package com.academy.mars.entity;

import com.academy.mars.entity.Courses;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "course_enrollments")
public class CourseEnrollments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Courses course;

    // Constructors
    public CourseEnrollments() {}

    public CourseEnrollments(Student student, Courses course) {
        this.student = student;
        this.course = course;
    }


    @Override
    public String toString() {
        return "CourseEnrollments{" +
                "id=" + id +
                ", student=" + student.getUser().getUsername() +
                ", course=" + course.getName() +
                '}';
    }
}
