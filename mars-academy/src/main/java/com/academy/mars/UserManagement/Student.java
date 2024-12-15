package com.academy.mars.UserManagement;

import com.academy.mars.AccessManagement.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "students")
public class Student extends User {
    // commented until we make those classes
    // @OneToMany(mappedBy = "student")
    // private List<Course> enrolledCourses;

    // @OneToMany(mappedBy = "student")
    // private List<Grade> gradesList;

    // @OneToMany(mappedBy = "student")
    // private List<Notification> notificationsList;
    public Student() {
        super();
        this.role = Role.Student;
    }

    public Student(Long id,
                   String name,
                   String email,
                   String password,
                   String profilePicture,
                   Role role,
                   LocalDateTime timeJoined) {
        super(id, name, email, password, profilePicture, Role.Student, timeJoined);
    }
}
