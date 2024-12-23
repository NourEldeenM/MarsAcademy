package com.academy.mars.repository;

import com.academy.mars.entity.Grade;
import com.academy.mars.entity.Student;
import com.academy.mars.entity.Quiz;
import com.academy.mars.entity.Assignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeRepositoryTest {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private Student student;

    @Mock
    private Quiz quiz;

    @Mock
    private Assignment assignment;

    private Grade grade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        grade = new Grade();
        grade.setId(1L);
        grade.setStudent(student);
        grade.setQuiz(quiz);
        grade.setAssignment(assignment);
    }

    @Test
    void findByCourseId() {
        when(gradeRepository.findByCourseId(anyLong())).thenReturn(List.of(grade));
        List<Grade> grades = gradeRepository.findByCourseId(1L);
        assertNotNull(grades);
        assertEquals(1, grades.size());
        verify(gradeRepository, times(1)).findByCourseId(1L);
    }

    @Test
    void findByStudentIdAndQuizId() {
        when(gradeRepository.findByStudentIdAndQuizId(anyLong(), anyLong())).thenReturn(Optional.of(grade));
        Optional<Grade> foundGrade = gradeRepository.findByStudentIdAndQuizId(1L, 1L);
        assertTrue(foundGrade.isPresent());
        assertEquals(grade, foundGrade.get());
        verify(gradeRepository, times(1)).findByStudentIdAndQuizId(1L, 1L);
    }

    @Test
    void findByStudentIdAndAssignmentId() {
        when(gradeRepository.findByStudentIdAndAssignmentId(anyLong(), anyLong())).thenReturn(Optional.of(grade));
        Optional<Grade> foundGrade = gradeRepository.findByStudentIdAndAssignmentId(1L, 1L);
        assertTrue(foundGrade.isPresent());
        assertEquals(grade, foundGrade.get());
        verify(gradeRepository, times(1)).findByStudentIdAndAssignmentId(1L, 1L);
    }

    @Test
    void deleteByStudentIdAndQuizId() {
        doNothing().when(gradeRepository).deleteByStudentIdAndQuizId(anyLong(), anyLong());
        gradeRepository.deleteByStudentIdAndQuizId(1L, 1L);
        verify(gradeRepository, times(1)).deleteByStudentIdAndQuizId(1L, 1L);
    }

    @Test
    void deleteByStudentIdAndAssignmentId() {
        doNothing().when(gradeRepository).deleteByStudentIdAndAssignmentId(anyLong(), anyLong());
        gradeRepository.deleteByStudentIdAndAssignmentId(1L, 1L);
        verify(gradeRepository, times(1)).deleteByStudentIdAndAssignmentId(1L, 1L);
    }
}
