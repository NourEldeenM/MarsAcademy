package com.academy.mars.UserManagement;

import com.academy.mars.AccessManagement.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "admins")
public class Admin extends User {
    public Admin() {
        super();
        this.role = Role.Admin;
    }

    public Admin(Long id,
                 String name,
                 String email,
                 String password,
                 String profilePicture,
                 Role role,
                 LocalDateTime timeJoined) {
        super(id, name, email, password, profilePicture, Role.Admin, timeJoined);
    }
}
