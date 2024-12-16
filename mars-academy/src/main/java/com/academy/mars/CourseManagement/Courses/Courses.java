package com.academy.mars.CourseManagement.Courses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Courses {
    @Id
    @Column(name = "name", unique = true)
    private String name;
    private String title;
    private String description;
    private String category;
    private Integer duration;

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
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
