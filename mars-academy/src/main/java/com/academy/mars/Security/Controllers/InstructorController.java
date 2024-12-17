package com.academy.mars.Security.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
//@PreAuthorize("hasRole('INSTRUCTOR')")
public class InstructorController {

    @Operation(summary = "Instructor Dashboard", description = "Access the instructor dashboard")
    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok("Welcome, Instructor!");
    }
}