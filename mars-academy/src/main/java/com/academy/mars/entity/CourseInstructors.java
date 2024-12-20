package com.academy.mars.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_instructors")
@Getter
@Setter
@NoArgsConstructor
public class CourseInstructors {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    private Courses course;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    public CourseInstructors(Courses course, User instructor) {
        this.course = course;
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "CourseInstructors{" +
                "id=" + id +
                ", course=" + (course != null ? course.getName() : null) +
                ", instructor=" + (instructor != null ? instructor.getUsername() : null) +
                '}';
    }
}
