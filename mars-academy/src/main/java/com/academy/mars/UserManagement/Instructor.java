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
@Table(name = "instructors")
public class Instructor extends User {
    @Column(nullable = false, length = 100)
    private String specialty;

    public Instructor() {
        super();
        this.role = Role.Instructor;
    }

    public Instructor(Long id,
                      String name,
                      String email,
                      String password,
                      String profilePicture,
                      Role role,
                      LocalDateTime timeJoined,
                      String specialty) {
        super(id, name, email, password, profilePicture, Role.Instructor, timeJoined);
        this.specialty = specialty;
    }
}
