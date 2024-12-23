package com.academy.mars.controller;

import com.academy.mars.entity.Courses;
import com.academy.mars.entity.Student;
import com.academy.mars.service.CourseEnrollmentsServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/enrollments")
public class CourseEnrollmentsController {

    private final CourseEnrollmentsServices courseEnrollmentsServices;

    public CourseEnrollmentsController(CourseEnrollmentsServices courseEnrollmentsServices) {
        this.courseEnrollmentsServices = courseEnrollmentsServices;
    }

    // Get all students enrolled in a course
    @GetMapping("/students")
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    public ResponseEntity<?> getStudentsEnrollInACourse(@RequestBody Map<String, Long> request) {
        Long courseId = request.get("courseId");
        if(courseId==null){
            return ResponseEntity.status(400).body(json("Error", "course id must be sent in body"));
        }
        try {
            List<Student> students = courseEnrollmentsServices.getAllStudentsInCourse(courseId);
            if (students.isEmpty()) {
                return ResponseEntity.status(200).body(json("Message", "This course doesn't have any students"));
            }
            return ResponseEntity.status(200).body(students);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    // Get all courses a specific student is enrolled in
    @GetMapping("/courses")
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','STUDENT')")
    public ResponseEntity<?> getCoursesOfStudent(@RequestBody Map<String, Long> request) {
        Long studentId = request.get("studentId");
        if(studentId==null){
            return ResponseEntity.status(400).body(json("Error", "student id must be sent in body"));
        }
        try {
            List<Courses> courses = courseEnrollmentsServices.findAllCoursesStudentEnrollIn(studentId);
            if (courses.isEmpty()) {
                return ResponseEntity.status(200).body(json("Message", "This student doesn't have any courses"));
            }
            return ResponseEntity.status(200).body(courses);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    // Enroll a student in a course
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','STUDENT')")
    public ResponseEntity<?> studentEnrollInACourse(@RequestBody Map<String, Long> request) {
        Long courseId = request.get("courseId");
        Long studentId = request.get("studentId");
        if(studentId==null){
            return ResponseEntity.status(400).body(json("Error", "student id must be sent in body"));
        }
        if(courseId==null){
            return ResponseEntity.status(400).body(json("Error", "course id must be sent in body"));
        }
        try {
            courseEnrollmentsServices.enrollStudentInCourse(courseId, studentId);
            return ResponseEntity.status(201).body(json("Message", "Student " + studentId + " enrolled in course " + courseId));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    // Unenroll a student from a course
    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','STUDENT')")
    public ResponseEntity<?> studentUnenrollInACourse(@RequestBody Map<String, Long> request) {
        Long courseId = request.get("courseId");
        Long studentId = request.get("studentId");
        if(studentId==null){
            return ResponseEntity.status(400).body(json("Error", "student id must be sent in body"));
        }
        if(courseId==null){
            return ResponseEntity.status(400).body(json("Error", "course id must be sent in body"));
        }
        try {
            courseEnrollmentsServices.unEnrollStudentInCourse(courseId, studentId);
            return ResponseEntity.status(200).body(json("Message", "Student " + studentId + " is not enrolled in course " + courseId));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    private Object json(String key, String value) {
        return Map.of(key, value);
    }
}
