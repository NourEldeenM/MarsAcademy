package com.academy.mars.controller;

import com.academy.mars.service.LessonsServices;
import com.academy.mars.entity.Lessons;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/lessons")
public class LessonsController {

    private final LessonsServices lessonsServices;

    @Autowired
    public LessonsController(LessonsServices lessonsServices) {
        this.lessonsServices = lessonsServices;
    }

    // Add a new lesson to a course
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    public ResponseEntity<?> addLesson(@PathVariable Long courseId, @RequestBody Lessons lesson) {
        try {
            Lessons savedLesson = lessonsServices.addLesson(courseId, lesson);
            return ResponseEntity.status(201).body(savedLesson);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(400).body(json("Error", ex.getMessage()));
        }
    }

    // Get all lessons for a specific course
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','STUDENT')")
    public ResponseEntity<?> getAllLessons(@PathVariable Long courseId) {
        try{

            List<Lessons> lessons = lessonsServices.getAllLessons(courseId);
            return ResponseEntity.status(200).body(lessons);

        }catch (Exception ex){
            return ResponseEntity.status(400).body(json("Error", ex.getMessage().toString()));
        }
    }

    // Get a specific lesson by its ID
    @GetMapping("/{lessonId}")
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','STUDENT')")
    public ResponseEntity<?> getLesson(@PathVariable Long lessonId) {
        try {
            Lessons lesson = lessonsServices.getLesson(lessonId);
            return ResponseEntity.status(200).body(lesson);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(400).body(json("Error", ex.getMessage().toString()));

        }
    }

    // Update an existing lesson
    @PutMapping("/{lessonId}")
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    public ResponseEntity<?> updateLesson(@PathVariable Long courseId,@PathVariable Long lessonId, @RequestBody Lessons updatedLesson) {
        try {
            Lessons updated = lessonsServices.updateLesson(courseId,lessonId, updatedLesson);
            return ResponseEntity.status(200).body(updated);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(400).body(json("Error", ex.getMessage().toString()));

        }
    }

    // Delete a lesson by its ID
    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    public ResponseEntity<?> deleteLesson(@PathVariable Long courseId,@PathVariable Long lessonId) {
        try {
            lessonsServices.deleteLesson(courseId , lessonId);
            return ResponseEntity.status(200).body(json("message", "Successfully deleted lesson"));

        } catch (RuntimeException ex) {
            return ResponseEntity.status(400).body(json("Error", ex.getMessage().toString()));

        }

    }

    //delete all lessons of a course
    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteAllLessonsOfCourse(@PathVariable Long courseId) {
        try {
            lessonsServices.deleteAllLessonsOfCourse(courseId );
            return ResponseEntity.status(200).body(json("message", "lessons deleted Successfully"));

        } catch (RuntimeException ex) {
            return ResponseEntity.status(400).body(json("Error", ex.getMessage().toString()));

        }

    }

    private ObjectNode json(String key, String value) {
        // Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Create an ObjectNode (represents a JSON object)
        ObjectNode jsonObject = objectMapper.createObjectNode();

        // Add properties to the JSON object
        jsonObject.put(key, value);
        return jsonObject;
    }
}
