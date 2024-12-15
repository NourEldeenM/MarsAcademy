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
public class Instructor extends User {
    @Column(nullable = false, length = 100)
    private String specialty;

    public Instructor() {
        super();
    }

    public Instructor(Long id,
                      String name,
                      String email,
                      String password,
                      String profilePicture,
                      Role role,
                      Date timeJoined,
                      String specialty) {
        super(id, name, email, password, profilePicture, Role.Instructor, timeJoined);
        this.specialty = specialty;
    }
}
