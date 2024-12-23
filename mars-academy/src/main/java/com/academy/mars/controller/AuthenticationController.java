package com.academy.mars.controller;

import com.academy.mars.config.JwtUtils;
import com.academy.mars.service.AuthenticationService;
import com.academy.mars.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final JwtUtils jwtService;
    private final AuthenticationService authService;

    @Operation(summary = "User Signup", description = "Register a new user")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        Map<String, Object> response = authService.registerNewUser(user);
        user.setId((Long) response.get("id"));
        response.remove("id");
        String token = jwtService.generateToken(user);
        response.put("token", token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "User Login", description = "Authenticate user and generate a JWT token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Map<String, Object> response = authService.login(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
