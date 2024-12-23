package com.academy.mars.entity;

import com.academy.mars.entity.Courses;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Lessons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String content;
    private int duration;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Courses course;

    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private LessonsOtp lessonOtp;

    public Lessons(String title, String description, String content, int duration, Courses course) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.duration = duration;
        this.course = course;
    }
}
