package com.academy.mars.controller;

import com.academy.mars.entity.CourseInstructors;
import com.academy.mars.entity.Instructor;
import com.academy.mars.service.CourseInstructorsServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/courses/{courseId}/instructors")
public class CourseInstructorsController {
    private final CourseInstructorsServices courseInstructorsServices;

    public CourseInstructorsController(CourseInstructorsServices courseInstructorsServices) {
        this.courseInstructorsServices = courseInstructorsServices;
    }

    @GetMapping
    public ResponseEntity<?> getInstructorsOfACourse(@PathVariable Long courseId) {
        try {
            List<CourseInstructors> courseInstructors = courseInstructorsServices.getInstructorsByCourse(courseId);
            if (courseInstructors.isEmpty()) {
                return ResponseEntity.status(200).body(json("Message", "This course doesn't have any instructors"));
            }

            // Extracting instructor details from courseInstructors
            List<Instructor> instructors = courseInstructors.stream()
                    .map(CourseInstructors::getInstructor)
                    .toList();

            return ResponseEntity.status(200).body(instructors);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }
    @PostMapping("/{instructorId}")
    public ResponseEntity<?> addCourseInstructor(@PathVariable  Long courseId,@PathVariable Long instructorId) {
        try{
            courseInstructorsServices.addInstructorToCourse(courseId,instructorId);
            return ResponseEntity.status(201).body(json("Message","instructor "+ instructorId +" enroll in course "+courseId));
        }catch (Exception e){
            return ResponseEntity.status(400).body(json("Error",e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeAllInstructorsFromCourse(@PathVariable  Long courseId) {
        try {
            courseInstructorsServices.removeAllInstructorsFromCourse(courseId);
            return ResponseEntity.status(200).body(json("Message","All instructors of course with course id "+ courseId +" is removed."));
        }catch (Exception e){
            return ResponseEntity.status(400).body(json("Error",e.getMessage()));
        }
    }
    @DeleteMapping("/{instructorId}")
    public ResponseEntity<?> removeInstructorFromCourse(@PathVariable  Long courseId,@PathVariable Long instructorId) {
        try {
            courseInstructorsServices.removeInstructorFromCourse(courseId,instructorId);
            return ResponseEntity.status(200).body(json("Message","instructor "+ instructorId +" is not enrolled in course "+courseId));
        }catch (Exception e){
            return ResponseEntity.status(400).body(json("Error",e.getMessage()));
        }
    }

    private Object json(String key, String message) {
        return Map.of(key, message);
    }
}
