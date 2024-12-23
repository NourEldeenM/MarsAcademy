package com.academy.mars.service;

import com.academy.mars.entity.Courses;
import com.academy.mars.entity.QuestionBank;
import com.academy.mars.repository.CoursesRepository;
import com.academy.mars.repository.QuestionBankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class QuestionBankServiceTest {

    @Mock
    private QuestionBankRepository questionBankRepository;

    @Mock
    private CoursesRepository courseRepository;

    @InjectMocks
    private QuestionBankService questionBankService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createQuestionBank() {
        long courseId = 1L;
        Courses course = new Courses();
        course.setId(courseId);
        QuestionBank questionBank = new QuestionBank();
        questionBank.setCourse(course);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(questionBankRepository.save(questionBank)).thenReturn(questionBank);
        questionBankService.createQuestionBank(courseId, questionBank);
        verify(courseRepository, times(1)).findById(courseId);
        verify(questionBankRepository, times(1)).save(questionBank);
    }

    @Test
    void getQuestionBanks() {
        long courseId = 1L;
        QuestionBank questionBank1 = new QuestionBank();
        QuestionBank questionBank2 = new QuestionBank();
        when(questionBankRepository.findByCourseId(courseId)).thenReturn(List.of(questionBank1, questionBank2));
        List<QuestionBank> result = questionBankService.getQuestionBanks(courseId);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(questionBankRepository, times(1)).findByCourseId(courseId);
    }

    @Test
    void deleteQuestionBank() {
        long questionBankId = 1L;
        when(questionBankRepository.existsById(questionBankId)).thenReturn(true);
        boolean result = questionBankService.deleteQuestionBank(questionBankId);
        assertTrue(result);
        verify(questionBankRepository, times(1)).deleteById(questionBankId);
    }

    @Test
    void deleteQuestionBankNotFound() {
        long questionBankId = 1L;
        when(questionBankRepository.existsById(questionBankId)).thenReturn(false);
        boolean result = questionBankService.deleteQuestionBank(questionBankId);
        assertFalse(result);
        verify(questionBankRepository, times(0)).deleteById(questionBankId);
    }

    @Test
    void getQuestionBankByCourse() {
        long courseId = 1L;
        QuestionBank questionBank1 = new QuestionBank();
        QuestionBank questionBank2 = new QuestionBank();
        when(questionBankRepository.findByCourseId(courseId)).thenReturn(List.of(questionBank1, questionBank2));
        List<QuestionBank> result = questionBankService.getQuestionBankByCourse(courseId);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(questionBankRepository, times(1)).findByCourseId(courseId);
    }
}
