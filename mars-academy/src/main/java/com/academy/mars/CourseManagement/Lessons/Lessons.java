package com.academy.mars.CourseManagement.Lessons;

import com.academy.mars.entity.courses.Courses;
import jakarta.persistence.*;

@Entity
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

    // Constructors, getters, and setters

    public Lessons() {}

    public Lessons(String title, String description, String content, int duration, Courses course) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.duration = duration;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }
}
