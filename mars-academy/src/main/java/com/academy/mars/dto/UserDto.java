package com.academy.mars.dto;

import com.academy.mars.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;        // Unique identifier
    private String username;  // User's chosen username
    private String email;     // User's email
    private UserRole userRole; // Role of the user (e.g., ADMIN, STUDENT, INSTRUCTOR)
}
