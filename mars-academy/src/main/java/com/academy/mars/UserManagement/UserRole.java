package com.academy.mars.UserManagement;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public enum UserRole {
    ROLE_ADMIN,
    ROLE_INSTRUCTOR,
    ROLE_STUDENT,
    User;//add user role to continue check the course enrollments


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
