package com.academy.mars.UserManagement;

import com.academy.mars.AccessManagement.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Setter
@Getter
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class Student extends User {
    // commented until we make those classes
//    private List<Course> enrolledCourses;
//    private List<Grade> gradesList;
//    private List<Notification> notificationsList;

    public Student() {
        super();
    }

    public Student(Long id,
                   String name,
                   String email,
                   String password,
                   String profilePicture,
                   Role role,
                   Date timeJoined) {
        super(id, name, email, password, profilePicture, Role.Student, timeJoined);
    }
}
