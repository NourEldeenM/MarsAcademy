package com.academy.mars.CourseManagement.CourseEnrollments;

import com.academy.mars.entity.User;
import com.academy.mars.entity.Courses;
import jakarta.persistence.*;

@Entity
@Table(name = "course_enrollments")
public class CourseEnrollments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Courses course;

    // Constructors
    public CourseEnrollments() {}

    public CourseEnrollments(User user, Courses course) {
        this.user = user;
        this.course = course;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "CourseEnrollments{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", course=" + course.getName() +
                '}';
    }
}
