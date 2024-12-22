package com.academy.mars.controller;

import com.academy.mars.entity.LessonsOtp;
import com.academy.mars.service.LessonsOtpServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/lessons/{lessonId}/otp")
public class LessonsOtpController {

    private final LessonsOtpServices lessonsOtpServices;

    public LessonsOtpController(LessonsOtpServices lessonsOtpServices) {
        this.lessonsOtpServices = lessonsOtpServices;
    }

    // POST: Create OTP for a lesson
    @PostMapping
    public ResponseEntity<?> createOtp(@PathVariable Long lessonId) {
        try {
            LessonsOtp lessonsOtp = lessonsOtpServices.createOtpForLesson(lessonId);
            return ResponseEntity.status(201).body(lessonsOtp);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    // GET: Get OTP for a lesson
    @GetMapping
    public ResponseEntity<?> getOtp(@PathVariable Long lessonId) {
        try {
            LessonsOtp lessonsOtp = lessonsOtpServices.getOtpForLesson(lessonId);
            return ResponseEntity.status(200).body(lessonsOtp);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    // PUT: Update OTP for a lesson
    @PutMapping
    public ResponseEntity<?> updateOtp(@PathVariable Long lessonId) {
        try {
            LessonsOtp updatedOtp = lessonsOtpServices.updateOtpForLesson(lessonId);
            return ResponseEntity.status(200).body(updatedOtp);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    // DELETE: Delete OTP for a lesson
    @DeleteMapping
    public ResponseEntity<?> deleteOtp(@PathVariable Long lessonId) {
        try {
            lessonsOtpServices.deleteOtpForLesson(lessonId);
            return ResponseEntity.status(200).body(json("Message", "OTP for lesson " + lessonId + " has been deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    // Helper method to format JSON response
    private Object json(String key, String message) {
        return Map.of(key, message);
    }
}
