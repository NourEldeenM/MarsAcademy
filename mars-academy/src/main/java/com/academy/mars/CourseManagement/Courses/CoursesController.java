package com.academy.mars.CourseManagement.Courses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping
    public ResponseEntity<?> getCourses(
            @RequestParam(required = false) String name ,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category

    ){
        try {
            if (name == null && title == null && category == null) {
                return ResponseEntity.ok(coursesServices.getCourses());
            }

            if (name != null && title == null && category == null) {
                return ResponseEntity.ok(List.of(coursesServices.getCourseByName(name)));
            } else if (name == null && title != null && category == null) {
                return ResponseEntity.ok(coursesServices.getCoursesByTitle(title));
            } else if (name == null && title == null && category != null) {
                return ResponseEntity.ok(coursesServices.getCoursesByCategory(category));
            }

            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Create an ObjectNode (represents a JSON object)
            ObjectNode jsonObject = objectMapper.createObjectNode();

            // Add properties to the JSON object
            jsonObject.put("Error", "Invalid combination of filters provided.");

            return ResponseEntity.badRequest()
                    .body(jsonObject);
        } catch (Exception ex) {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Create an ObjectNode (represents a JSON object)
            ObjectNode jsonObject = objectMapper.createObjectNode();

            // Add properties to the JSON object
            jsonObject.put("Error", ex.getMessage().toString());

            return ResponseEntity.status(500)
                    .body(jsonObject);
        }
    }

    //add new course
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Courses course) {

        try {
            Courses courses = coursesServices.addCourse(course);
            return ResponseEntity.status(201).body(courses);

        }catch (Exception ex){
            //course name exist

            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Create an ObjectNode (represents a JSON object)
            ObjectNode jsonObject = objectMapper.createObjectNode();

            // Add properties to the JSON object
            jsonObject.put("Error", ex.getMessage().toString());
            return ResponseEntity.status(409).body(jsonObject) ;
        }
    }

    // Update an existing course
    @PutMapping
    public ResponseEntity<?> updateCourse(@RequestBody Courses updatedCourse) {
        // Validate the course name or other required fields
        if (updatedCourse.getName() == null || updatedCourse.getName().isEmpty()) {
            return ResponseEntity.status(400).body("Course name is required");
        }

        try {
        // Check if the course exists by ID (or other unique identifier)
        Courses existingCourse = coursesServices.getCourseByName(updatedCourse.getName());
        if (existingCourse == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonObject = objectMapper.createObjectNode();
            jsonObject.put("Error", "Course not found");
            return ResponseEntity.status(404).body(jsonObject);
        }

        // Check if the course name can be changed (depending on your business logic)
        if (coursesServices.getCourseByName(updatedCourse.getName()) != null) {
            // If the name can't be changed, return a conflict
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonObject = objectMapper.createObjectNode();
            jsonObject.put("Error", "Course name can't be changed");
            return ResponseEntity.status(409).body(jsonObject);
        }
            coursesServices.updateCourse(updatedCourse);
            return ResponseEntity.status(200).body(updatedCourse);  // Return the updated course
        } catch (Exception ex) {
            // Handle any unexpected errors
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonObject = objectMapper.createObjectNode();
            jsonObject.put("Error", ex.getMessage());
            return ResponseEntity.status(400).body(jsonObject);
        }

        // Proceed with the update
    }


    // Delete a course by name
    @DeleteMapping
    public ResponseEntity<?> deleteCourse(@RequestParam String name) {
        try {
            // Check if the course exists by ID (or other unique identifier)
            Courses existingCourse = coursesServices.getCourseByName(name);
            if (existingCourse == null) {
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode jsonObject = objectMapper.createObjectNode();
                jsonObject.put("Error", "Course not found");
                return ResponseEntity.status(404).body(jsonObject);
            }
            coursesServices.deleteCourse(name);
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonObject = objectMapper.createObjectNode();
            jsonObject.put("Error", "Course with name '" + name + "' has been deleted successfully.");
            return ResponseEntity.status(200).body(jsonObject);
        }catch (Exception ex){
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonObject = objectMapper.createObjectNode();
            jsonObject.put("Error", ex.getMessage());
            return ResponseEntity.status(400).body(jsonObject);
        }

    }
}
