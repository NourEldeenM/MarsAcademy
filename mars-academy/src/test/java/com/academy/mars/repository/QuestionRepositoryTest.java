package com.academy.mars.repository;

import com.academy.mars.entity.Question;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionRepositoryTest {

    @Mock
    private QuestionRepository questionRepository;

    @Test
    void findByQuizId() {
        long quizId = 1L;
        List<Question> mockQuestions = Arrays.asList(new Question(), new Question());  // 2 questions
        when(questionRepository.findByQuizId(quizId)).thenReturn(mockQuestions);
        List<Question> questions = questionRepository.findByQuizId(quizId);
        assertEquals(2, questions.size());
        verify(questionRepository, times(1)).findByQuizId(quizId);
    }

    @Test
    void existsById() {
        long questionId = 1L;
        when(questionRepository.existsById(questionId)).thenReturn(true);
        boolean exists = questionRepository.existsById(questionId);
        assertTrue(exists);
        verify(questionRepository, times(1)).existsById(questionId);
    }

    @Test
    void deleteById() {
        long questionId = 1L;
        questionRepository.deleteById(questionId);
        verify(questionRepository, times(1)).deleteById(questionId);
    }

    @Test
    void findByCourseId() {
        long courseId = 1L;
        List<Question> mockQuestions = Arrays.asList(new Question(), new Question());
        when(questionRepository.findByCourseId(courseId)).thenReturn(mockQuestions);
        List<Question> questions = questionRepository.findByCourseId(courseId);
        assertEquals(2, questions.size());
        verify(questionRepository, times(1)).findByCourseId(courseId);
    }

    @Test
    void findByQuestionBankId() {
        long questionBankId = 1L;
        List<Question> mockQuestions = Arrays.asList(new Question(), new Question());
        when(questionRepository.findByQuestionBankId(questionBankId)).thenReturn(mockQuestions);
        List<Question> questions = questionRepository.findByQuestionBankId(questionBankId);
        assertEquals(2, questions.size());
        verify(questionRepository, times(1)).findByQuestionBankId(questionBankId);
    }

    @Test
    void findByAssignmentId() {
        long assignmentId = 1L;
        List<Question> mockQuestions = Arrays.asList(new Question(), new Question());
        when(questionRepository.findByAssignmentId(assignmentId)).thenReturn(mockQuestions);
        List<Question> questions = questionRepository.findByAssignmentId(assignmentId);
        assertEquals(2, questions.size());
        verify(questionRepository, times(1)).findByAssignmentId(assignmentId);
    }
}
