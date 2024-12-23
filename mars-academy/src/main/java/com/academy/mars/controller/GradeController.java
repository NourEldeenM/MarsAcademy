package com.academy.mars.controller;


import com.academy.mars.entity.*;
import com.academy.mars.service.CoursesServices;
import com.academy.mars.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/courses")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CoursesServices coursesServices;

    @PostMapping({"/{courseId}/students/{studentId}/instructors/{instructorId}/grades"})
    public ResponseEntity<String> gradeStudent(@PathVariable long courseId,
                                               @PathVariable long studentId,
                                               @PathVariable long instructorId,
                                               @RequestBody Grade grade) {
        Student student = new Student();
        student.setId(studentId);
        grade.setStudent(student);
        Instructor instructor = new Instructor();
        instructor.setId(instructorId);
        grade.setInstructor(instructor);
        Courses course = new Courses();
        course.setId(courseId);
        grade.setCourse(course);

        gradeService.gradeStudent(courseId, studentId, instructorId, grade);
        return ResponseEntity.ok("Grade assigned successfully!");
    }

    @PostMapping({"/{courseId}/quizzes/{quizId}/grades", "/{courseId}/assignments/{assignmentId}/grades"})
    public ResponseEntity<String> submitGrade(@PathVariable long courseId,
                                              @PathVariable(required = false) Long quizId,
                                              @PathVariable(required = false) Long assignmentId,
                                              @RequestBody Grade grade) {
        Courses course = new Courses();
        course.setId(courseId);
        grade.setCourse(course);

        if (quizId != null) {
            Quiz quiz = new Quiz();
            quiz.setId(quizId);
            grade.setQuiz(quiz);
        } else if (assignmentId != null) {
            Assignment assignment = new Assignment();
            assignment.setId(assignmentId);
            grade.setAssignment(assignment);
            grade.setManualFeedback(true);
        }
        gradeService.createGrade(grade);
        return ResponseEntity.ok("Grade submitted successfully!");
    }

    @GetMapping("/{courseId}/assignments/{assignmentId}/feedback")
    public ResponseEntity<String> getFeedbackForAssignment(@PathVariable long courseId,
                                                           @PathVariable long assignmentId,
                                                           @RequestParam long studentId) {
        Optional<Grade> gradeOptional = gradeService.getGradeByStudentAndAssignment(studentId, assignmentId);
        if (gradeOptional.isPresent()) {
            return ResponseEntity.ok(gradeOptional.get().getFeedback());
        }
        return ResponseEntity.status(404).body("Feedback not found.");
    }

    @PostMapping("/{courseId}/assignments/{assignmentId}/feedback")
    public ResponseEntity<String> provideManualFeedback(@PathVariable long courseId,
                                                        @PathVariable long assignmentId,
                                                        @RequestBody Grade grade) {
        grade.setManualFeedback(true);
        Courses course = new Courses();
        course.setId(courseId);
        grade.setCourse(course);
        Assignment assignment = new Assignment();
        assignment.setId(assignmentId);
        grade.setAssignment(assignment);
        gradeService.createGrade(grade);
        return ResponseEntity.ok("Manual feedback provided successfully!");
    }

    @PatchMapping("/{courseId}/assignments/{assignmentId}/feedback")
    public ResponseEntity<String> updateAssignmentFeedback(@PathVariable long courseId,
                                                           @PathVariable long assignmentId,
                                                           @RequestParam long gradeId,
                                                           @RequestParam String feedback) {
        gradeService.updateFeedback(gradeId, feedback, true);
        return ResponseEntity.ok("Feedback updated successfully!");
    }

    @GetMapping("/{courseId}/grades")
    public List<Grade> getGradesForCourse(@PathVariable long courseId) {
        return gradeService.getGradesByCourseId(courseId);
    }

    @GetMapping({"/{courseId}/quizzes/{quizId}/grades", "/{courseId}/assignments/{assignmentId}/grades"})
    public ResponseEntity<Grade> getGradeByType(@PathVariable long courseId,
                                                @PathVariable(required = false) Long quizId,
                                                @PathVariable(required = false) Long assignmentId,
                                                @RequestParam long studentId) {
        Optional<Grade> grade;
        if (quizId != null) {
            grade = gradeService.getGradeByStudentAndQuiz(studentId, quizId);
        } else {
            grade = gradeService.getGradeByStudentAndAssignment(studentId, assignmentId);
        }

        return grade.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping({"/{courseId}/quizzes/{quizId}/grades", "/{courseId}/assignments/{assignmentId}/grades"})
    public ResponseEntity<String> updateGrade(@PathVariable long courseId,
                                              @PathVariable(required = false) Long quizId,
                                              @PathVariable(required = false) Long assignmentId,
                                              @RequestParam long studentId,
                                              @RequestBody Grade grade) {
        Courses course = new Courses();
        course.setId(courseId);
        grade.setCourse(course);

        if (quizId != null) {
            Quiz quiz = new Quiz();
            quiz.setId(quizId);
            grade.setQuiz(quiz);
        } else if (assignmentId != null) {
            Assignment assignment = new Assignment();
            assignment.setId(assignmentId);
            grade.setAssignment(assignment);
        }

        Student student = new Student();
        student.setId(studentId);
        grade.setStudent(student);

        gradeService.updateGrade(grade);
        return ResponseEntity.ok("Grade updated successfully!");
    }

    @DeleteMapping({"/{courseId}/quizzes/{quizId}/grades", "/{courseId}/assignments/{assignmentId}/grades"})
    public ResponseEntity<String> deleteGrade(@PathVariable long courseId,
                                              @PathVariable(required = false) Long quizId,
                                              @PathVariable(required = false) Long assignmentId,
                                              @RequestParam long studentId) {
        boolean isDeleted;
        if (quizId != null) {
            isDeleted = gradeService.deleteGradeByQuiz(studentId, quizId);
        } else {
            isDeleted = gradeService.deleteGradeByAssignment(studentId, assignmentId);
        }
        if (isDeleted) {
            return ResponseEntity.ok("Grade deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Grade not found");
        }
    }
}
