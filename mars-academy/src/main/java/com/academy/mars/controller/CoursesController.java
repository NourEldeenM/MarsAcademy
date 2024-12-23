package com.academy.mars.controller;

import com.academy.mars.entity.Courses;
import com.academy.mars.service.CoursesServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/courses")
public class CoursesController {
    private final CoursesServices coursesServices;

    @Autowired
    public CoursesController(CoursesServices coursesServices) {
        this.coursesServices = coursesServices;
    }


    //get all courses
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','STUDENT')")
    @GetMapping
    public ResponseEntity<?> getCourses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category

    ) {
        System.out.println("get courses");
        try {
            if (name == null && title == null && category == null) {
                return ResponseEntity.ok(coursesServices.getCourses());
            }

            if (name != null) {
                return ResponseEntity.ok(List.of(coursesServices.getCourseByName(name)));
            } else if (title != null) {
                return ResponseEntity.ok(coursesServices.getCoursesByTitle(title));
            } else if (category != null) {
                return ResponseEntity.ok(coursesServices.getCoursesByCategory(category));
            }

            return ResponseEntity.badRequest()
                    .body(json("Error", "Invalid combination of filters provided."));
        } catch (Exception ex) {
          return ResponseEntity.internalServerError().body(json("Error", ex.getMessage()));
        }
    }

    //add new course
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Courses course) {

        try {
            Courses courses = coursesServices.addCourse(course);
            return ResponseEntity.status(201).body(courses);
        } catch (Exception ex) {
            return ResponseEntity.status(409).body(json("Error", ex.getMessage().toString()));
        }
    }

    // Update an existing course
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    @PutMapping
    public ResponseEntity<?> updateCourse(@RequestBody Courses updatedCourse) {
        // Validate the course name or other required fields
        if (updatedCourse.getId() == null || updatedCourse.getName().isEmpty()) {
            return ResponseEntity.status(400).body("Course Id is required");
        }

        try {
            coursesServices.updateCourse(updatedCourse);
            return ResponseEntity.status(200).body(updatedCourse);
        } catch (Exception ex) {
            return ResponseEntity.status(400).body(json("Error", ex.getMessage()));
        }
    }


    // Delete a course by name
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    @DeleteMapping
    public ResponseEntity<?> deleteCourse(@RequestParam Long id) {
        try {
            if (!coursesServices.courseExist(id)) {
                return ResponseEntity.status(404).body(json("Error", "Course not found"));
            }
            coursesServices.deleteCourse(id);
            return ResponseEntity.status(200).body(json("Message", "Course with this id '" + id + "' has been deleted successfully."));
        } catch (Exception ex) {
            return ResponseEntity.status(400).body(json("Error", ex.getMessage()));
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
