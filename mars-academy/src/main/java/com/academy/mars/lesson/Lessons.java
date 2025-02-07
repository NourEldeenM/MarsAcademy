package com.academy.mars.lesson;

import com.academy.mars.course.Courses;
import com.academy.mars.lessonFile.LessonsFiles;
import com.academy.mars.lessonOtp.LessonsOtp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Lessons {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;
    private String description;
    private String content;
    private int duration;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Courses course;

    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private LessonsOtp lessonOtp;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_id")
    private List<LessonsFiles> files;
    public Lessons(String title, String description, String content, int duration, Courses course) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.duration = duration;
        this.course = course;
    }
}
