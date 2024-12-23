package com.academy.mars.service;

import com.academy.mars.entity.Grade;
import com.academy.mars.entity.Instructor;
import com.academy.mars.entity.Student;
import com.academy.mars.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;


    public List<Grade> getGradesByCourseId(long courseId) {
        return gradeRepository.findByCourseId(courseId);
    }

    public Optional<Grade> getGradeByStudentAndQuiz(long studentId, long quizId) {
        return gradeRepository.findByStudentIdAndQuizId(studentId, quizId);
    }

    public Optional<Grade> getGradeByStudentAndAssignment(long studentId, long assignmentId) {
        return gradeRepository.findByStudentIdAndAssignmentId(studentId, assignmentId);
    }

    public void updateGrade(Grade grade) {
        gradeRepository.save(grade);
    }

    public void updateFeedback(long gradeId, String feedback, boolean manualFeedback) {
        Optional<Grade> gradeOptional = gradeRepository.findById(gradeId);
        if (gradeOptional.isPresent()) {
            Grade grade = gradeOptional.get();
            grade.setFeedback(feedback);
            grade.setManualFeedback(manualFeedback);
            gradeRepository.save(grade);
        }
    }

    public boolean deleteGradeByQuiz(long studentId, long quizId) {
        Optional<Grade> grade = gradeRepository.findByStudentIdAndQuizId(studentId, quizId);
        if (grade.isPresent()) {
            gradeRepository.deleteByStudentIdAndQuizId(studentId, quizId);
            return true;
        }
        return false;
    }

    public boolean deleteGradeByAssignment(long studentId, long assignmentId) {
        Optional<Grade> grade = gradeRepository.findByStudentIdAndAssignmentId(studentId, assignmentId);
        if (grade.isPresent()) {
            gradeRepository.deleteByStudentIdAndAssignmentId(studentId, assignmentId);
            return true;
        }
        return false;
    }

    public void gradeStudent(long courseId, long studentId, long instructorId, Grade grade) {
        grade.setStudent(new Student());
        grade.getStudent().setId(studentId);
        grade.setInstructor(new Instructor());
        grade.getInstructor().setId(instructorId);
        if (grade.getQuiz() != null) {
            grade.setFeedback(generateAutomatedFeedbackForQuiz(grade.getQuiz().getGrade().getScore()));
            grade.setManualFeedback(false);
        }
        gradeRepository.save(grade);
    }


    private String generateAutomatedFeedbackForQuiz(int score) {
        if (score >= 90) {
            return "Excellent! Keep up the great work!";
        } else if (score >= 70) {
            return "Good job! But there's room for improvement.";
        } else {
            return "Needs improvement. Please review the material and try again.";
        }
    }

    public void createGrade(Grade grade) {
        if (grade.isManualFeedback()) {
            grade.setFeedback(grade.getFeedback());
        }
        gradeRepository.save(grade);
    }
}
