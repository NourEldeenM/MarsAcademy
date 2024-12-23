package com.academy.mars.controller;

import com.academy.mars.entity.Assignment;
import com.academy.mars.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping
    public String createAssignment(@PathVariable long courseId, @RequestBody Assignment assignment) {
        assignment.setCourseId(courseId);
        assignmentService.createAssignment(assignment);
        return "assignment created successfully!";
    }

    // Get all assignments for a course
    @GetMapping
    public List<Assignment> getAllAssignments(@PathVariable long courseId) {
        return assignmentService.getAllAssignmentsByCourseId(courseId);
    }

    @GetMapping("/{assignmentId}")
    public Assignment getAssignment(@PathVariable long courseId, @PathVariable Long assignmentId) {
        return assignmentService.getAssignmentByCourseIdAndAssignmentId(courseId, assignmentId);
    }

    @PatchMapping("/{assignmentId}")
    public String updateAssignment(@PathVariable long courseId, @PathVariable Long assignmentId, @RequestBody Assignment assignment) {
        assignment.setCourseId(courseId);
        assignment.setId(assignmentId);
        assignmentService.updateAssignment(courseId, assignmentId, assignment);
        return "assignment updated successfully!";
    }

    @DeleteMapping("/{assignmentId}")
    public String deleteAssignment(@PathVariable long courseId, @PathVariable Long assignmentId) {
        boolean deleted = assignmentService.deleteAssignment(courseId, assignmentId);
        if (deleted) {
            return "assignment deleted successfully!";
        } else {
            throw new RuntimeException("assignment not found");
        }
    }
}
