package com.academy.mars.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public enum UserRole {
    ROLE_ADMIN,
    ROLE_INSTRUCTOR,
    ROLE_STUDENT;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
