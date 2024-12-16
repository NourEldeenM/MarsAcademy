package com.academy.mars.UserManagement;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public enum UserRole {
    ADMIN,
    INSTRUCTOR,
    STUDENT,
    ;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
