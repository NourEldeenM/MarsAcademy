package com.academy.mars.CourseManagement.Courses;

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
            return ResponseEntity.badRequest()
                    .body("Invalid combination of filters provided.");
        } catch (Exception ex) {
            return ResponseEntity.status(500)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }

    @PostMapping
    public Courses addCourse(@RequestBody Courses course) {
        return coursesServices.addCourse(course);
    }

}
