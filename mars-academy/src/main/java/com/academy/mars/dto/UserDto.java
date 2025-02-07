package com.academy.mars.dto;

import com.academy.mars.user.UserRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private String id;        // Unique identifier
    private String username;  // User's chosen username
    private String email;     // User's email
    private UserRole userRole; // Role of the user (e.g., ADMIN, STUDENT, INSTRUCTOR)
}
