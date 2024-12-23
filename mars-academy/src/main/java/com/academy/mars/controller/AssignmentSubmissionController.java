package com.academy.mars.controller;

import com.academy.mars.entity.AssignmentSubmission;
import com.academy.mars.service.AssignmentSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/courses/{courseId}/assignments")
public class AssignmentSubmissionController {

    @Autowired
    private AssignmentSubmissionService submissionService;

    @PostMapping("/{assignmentId}/submissions")
    public ResponseEntity<AssignmentSubmission> submitAssignment(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @RequestParam Long studentId,
            @RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        AssignmentSubmission submission = submissionService.submitAssignment(assignmentId, studentId, file, courseId);
        return ResponseEntity.ok(submission);
    }


    @GetMapping("/submissions")
    public ResponseEntity<List<AssignmentSubmission>> getSubmissionsByStudentAndCourse(
            @RequestParam Long studentId, @PathVariable Long courseId) {
        List<AssignmentSubmission> submissions = submissionService.getSubmissionsByStudentAndCourse(studentId, courseId);
        if (submissions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(submissions);
    }

}
