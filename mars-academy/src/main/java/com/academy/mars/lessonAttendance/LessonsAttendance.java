package com.academy.mars.lessonAttendance;

import com.academy.mars.lesson.Lessons;
import com.academy.mars.student.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class LessonsAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id",nullable = false)
    @JsonIgnore
    private Lessons lesson;


    @ManyToOne
    @JoinColumn(name = "student_id",nullable = false)
    @JsonIgnore
    private Student student;

    public LessonsAttendance(Lessons lesson, Student student) {
        this.lesson = lesson;
        this.student = student;
    }

}
