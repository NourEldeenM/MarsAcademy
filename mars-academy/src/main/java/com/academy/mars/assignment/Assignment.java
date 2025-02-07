package com.academy.mars.assignment;

import com.academy.mars.grade.Grade;
import com.academy.mars.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private String courseId;
    private LocalDate dueDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private List<Question> questions;

}
