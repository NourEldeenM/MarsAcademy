package com.academy.mars.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
    private String type;
    private String correctAnswer;

    @ElementCollection
    private List<String> options;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "assignment_id", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Courses course;

    @ManyToOne
    @JoinColumn(name = "question_bank_id", referencedColumnName = "id")
    private QuestionBank questionBank;
}
