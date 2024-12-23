package com.academy.mars.service;

import com.academy.mars.entity.Question;
import com.academy.mars.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateQuestion() {
        long questionId = 1L;
        Question question = new Question();
        question.setDescription("Updated description");
        question.setType("MCQ");
        when(questionRepository.save(any(Question.class))).thenReturn(question);
        questionService.updateQuestion(questionId, question);
        verify(questionRepository, times(1)).save(question);
        assertEquals(questionId, question.getId());
    }

    @Test
    void deleteQuestion() {
        long questionId = 1L;
        when(questionRepository.existsById(questionId)).thenReturn(true);
        boolean result = questionService.deleteQuestion(questionId);
        assertTrue(result);
        verify(questionRepository, times(1)).deleteById(questionId);
        when(questionRepository.existsById(questionId)).thenReturn(false);
        result = questionService.deleteQuestion(questionId);
        assertFalse(result);
        verify(questionRepository, times(2)).existsById(questionId);
    }
}
