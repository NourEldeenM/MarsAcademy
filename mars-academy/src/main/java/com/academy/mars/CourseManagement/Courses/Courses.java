package com.academy.mars.CourseManagement.Courses;

import com.academy.mars.CourseManagement.CourseEnrollments.CourseEnrollments;
import com.academy.mars.CourseManagement.Lessons.Lessons;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Automatically generates the ID
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String title;
    private String description;
    private String category;
    private Integer duration;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CourseEnrollments> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Lessons> lessons = new ArrayList<>();

    public Courses(String name) {
        this.name = name;
    }

    @JsonCreator
    public Courses(@JsonProperty("name") String name,
                   @JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("category") String category,
                   @JsonProperty("duration") Integer duration) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.category = category;
        this.duration = duration;
    }

    public Courses() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
