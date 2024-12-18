package com.academy.mars.CourseManagement.CourseEnrollments;

import com.academy.mars.UserManagement.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/courses/{courseId}/enrollments")
public class CourseEnrollmentsController {
    CourseEnrollmentsServices courseEnrollmentsServices;

    public CourseEnrollmentsController(CourseEnrollmentsServices courseEnrollmentsServices) {
        this.courseEnrollmentsServices = courseEnrollmentsServices;
    }

    @GetMapping
    public ResponseEntity<?> getStudentsEnrollInACourse(@PathVariable  Long courseId) {
        try {
            List<User> students=courseEnrollmentsServices.getAllStudentsInCourse(courseId);
            if(students.isEmpty()){
                return ResponseEntity.status(200).body(json("Messge","This course doesn't have any students"));

            }
            return ResponseEntity.status(200).body(students);
        }catch (Exception e){
            return ResponseEntity.status(400).body(json("Error",e.getMessage()));
        }
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<?> studentEnrollInACourse(@PathVariable  Long courseId,@PathVariable Long studentId) {
        try{
            courseEnrollmentsServices.enrollStudentInCourse(courseId,studentId);
        }catch (Exception e){
            return ResponseEntity.status(400).body(json("Error",e.getMessage()));

        }
        return ResponseEntity.status(201).body(json("Message","student "+ studentId +" enroll in course "+courseId));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> studentUnenrollInACourse(@PathVariable  Long courseId,@PathVariable Long studentId) {
        try {
            courseEnrollmentsServices.unEnrollStudentInCourse(courseId,studentId);

            return ResponseEntity.status(200).body(json("Message","student "+ studentId +" is not enrolled in course "+courseId));
        }catch (Exception e){
            return ResponseEntity.status(400).body(json("Error",e.getMessage()));
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
