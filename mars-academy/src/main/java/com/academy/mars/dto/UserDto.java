package com.academy.mars.dto;

import com.academy.mars.entity.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;        // Unique identifier
    private String username;  // User's chosen username
    private String email;     // User's email
    private UserRole userRole; // Role of the user (e.g., ADMIN, STUDENT, INSTRUCTOR)
}
