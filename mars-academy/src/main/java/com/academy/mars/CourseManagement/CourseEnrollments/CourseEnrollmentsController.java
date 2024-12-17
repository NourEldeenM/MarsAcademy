package com.academy.mars.CourseManagement.CourseEnrollments;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/courses/{courseId}/enrollments")
public class CourseEnrollmentsController {
    @GetMapping
    public ResponseEntity<?> getStudentsEnrollInACourse(@PathVariable  Long courseId) {
        return ResponseEntity.status(200).body(json("Message","students"));
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<?> studentEnrollInACourse(@PathVariable  Long courseId,@PathVariable Long studentId) {
        return ResponseEntity.status(201).body(json("Message","student "+ studentId +" enroll in course "+courseId));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> studentUnenrollInACourse(@PathVariable  Long courseId,@PathVariable Long studentId) {
        return ResponseEntity.status(200).body(json("Message","student "+ studentId +" un enroll in course "+courseId));
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
