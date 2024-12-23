package com.academy.mars.service;

import com.academy.mars.entity.Grade;
import com.academy.mars.entity.Quiz;
import com.academy.mars.repository.GradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeService gradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getGradesByCourseId() {
        long courseId = 1L;
        Grade grade1 = new Grade();
        Grade grade2 = new Grade();
        when(gradeRepository.findByCourseId(courseId)).thenReturn(List.of(grade1, grade2));
        List<Grade> grades = gradeService.getGradesByCourseId(courseId);
        assertNotNull(grades);
        assertEquals(2, grades.size());
        verify(gradeRepository, times(1)).findByCourseId(courseId);
    }

    @Test
    void getGradeByStudentAndQuiz() {
        long studentId = 1L;
        long quizId = 1L;
        Grade grade = new Grade();
        when(gradeRepository.findByStudentIdAndQuizId(studentId, quizId)).thenReturn(Optional.of(grade));
        var result = gradeService.getGradeByStudentAndQuiz(studentId, quizId);
        assertTrue(result.isPresent());
        assertEquals(grade, result.get());
        verify(gradeRepository, times(1)).findByStudentIdAndQuizId(studentId, quizId);
    }

    @Test
    void getGradeByStudentAndAssignment() {
        long studentId = 1L;
        long assignmentId = 1L;
        Grade grade = new Grade();
        when(gradeRepository.findByStudentIdAndAssignmentId(studentId, assignmentId)).thenReturn(Optional.of(grade));
        var result = gradeService.getGradeByStudentAndAssignment(studentId, assignmentId);

        assertTrue(result.isPresent());
        assertEquals(grade, result.get());
        verify(gradeRepository, times(1)).findByStudentIdAndAssignmentId(studentId, assignmentId);
    }

    @Test
    void updateGrade() {
        Grade grade = new Grade();
        grade.setId(1L);
        gradeService.updateGrade(grade);
        verify(gradeRepository, times(1)).save(grade);
    }

    @Test
    void updateFeedback() {
        long gradeId = 1L;
        String feedback = "Good job!";
        boolean manualFeedback = true;
        Grade grade = new Grade();
        grade.setId(gradeId);
        grade.setFeedback(feedback);
        grade.setManualFeedback(manualFeedback);
        when(gradeRepository.findById(gradeId)).thenReturn(Optional.of(grade));
        gradeService.updateFeedback(gradeId, feedback, manualFeedback);
        assertEquals(feedback, grade.getFeedback());
        assertTrue(grade.isManualFeedback());
        verify(gradeRepository, times(1)).save(grade);
    }

    @Test
    void deleteGradeByQuiz() {
        long studentId = 1L;
        long quizId = 1L;
        Grade grade = new Grade();
        when(gradeRepository.findByStudentIdAndQuizId(studentId, quizId)).thenReturn(Optional.of(grade));
        boolean result = gradeService.deleteGradeByQuiz(studentId, quizId);
        assertTrue(result);
        verify(gradeRepository, times(1)).deleteByStudentIdAndQuizId(studentId, quizId);
    }

    @Test
    void deleteGradeByAssignment() {
        long studentId = 1L;
        long assignmentId = 1L;
        Grade grade = new Grade();
        when(gradeRepository.findByStudentIdAndAssignmentId(studentId, assignmentId)).thenReturn(Optional.of(grade));
        boolean result = gradeService.deleteGradeByAssignment(studentId, assignmentId);
        assertTrue(result);
        verify(gradeRepository, times(1)).deleteByStudentIdAndAssignmentId(studentId, assignmentId);
    }

    @Test
    void gradeStudent() {
        long courseId = 1L;
        long studentId = 1L;
        long instructorId = 1L;
        Grade grade = new Grade();
        grade.setQuiz(new Quiz());
        grade.getQuiz().setGrade(new Grade());
        grade.getQuiz().getGrade().setScore(95);
        gradeService.gradeStudent(courseId, studentId, instructorId, grade);
        verify(gradeRepository, times(1)).save(grade);
        assertNotNull(grade.getFeedback());
    }

    @Test
    void createGrade() {
        Grade grade = new Grade();
        grade.setManualFeedback(true);
        grade.setFeedback("Great work!");
        gradeService.createGrade(grade);
        verify(gradeRepository, times(1)).save(grade);
    }
}
