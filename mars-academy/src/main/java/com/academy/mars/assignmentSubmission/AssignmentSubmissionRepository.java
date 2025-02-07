package com.academy.mars.assignmentSubmission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, String> {
    List<AssignmentSubmission> findByStudentId(String studentId);
    List<AssignmentSubmission> findByStudentIdAndCourseId(String studentId, String courseId);
}

