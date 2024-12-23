package com.academy.mars.controller;

import com.academy.mars.entity.User;
import com.academy.mars.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')") // viewing all app users are only limited to admins
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                return ResponseEntity.status(200).body(json("Message", "No users found"));
            }
            return ResponseEntity.status(200).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    @GetMapping("/{userId}") // anyone can go to student's profile
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.status(200).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(json("Error", e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')") // admins can create users, otherwise sign up!
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(201).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    // anyone can update his profile
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(userId, user);
            return ResponseEntity.status(200).body(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    // anyone can delete his own account
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(200).body(json("Message", "User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    private ObjectNode json(String key, String value) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put(key, value);
        return jsonObject;
    }
}
