package com.academy.mars.controller;

import com.academy.mars.entity.AssignmentSubmission;
import com.academy.mars.service.AssignmentSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/courses/{courseId}/assignments")
public class AssignmentSubmissionController {

    @Autowired
    private AssignmentSubmissionService submissionService;

    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR')")    // students can submit assignments
    @PostMapping("/{assignmentId}/submissions")
    public ResponseEntity<AssignmentSubmission> submitAssignment(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @RequestParam Long studentId,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam(required = false) String fileLink) {

        if (file == null && (fileLink == null || fileLink.isEmpty())) {
            return ResponseEntity.badRequest().body(null);
        }
        if (fileLink != null && !fileLink.isEmpty()) {
            AssignmentSubmission submission = submissionService.submitAssignment(assignmentId, studentId, fileLink, courseId);
            return ResponseEntity.ok(submission);
        }

        if (!file.isEmpty()) {
            AssignmentSubmission submission = submissionService.submitAssignment(assignmentId, studentId, fileLink, courseId);
            return ResponseEntity.ok(submission);
        }
        return ResponseEntity.badRequest().body(null); // Invalid request
    }


    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")    // instructors and students can get submissions
    @GetMapping("/{assignmentId}/submissions")
    public ResponseEntity<List<AssignmentSubmission>> getSubmissionsByStudentAndCourse(
            @RequestParam Long studentId, @PathVariable Long courseId) {
        List<AssignmentSubmission> submissions = submissionService.getSubmissionsByStudentAndCourse(studentId, courseId);
        if (submissions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(submissions);
    }

}
