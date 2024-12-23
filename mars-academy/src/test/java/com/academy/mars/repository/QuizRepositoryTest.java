package com.academy.mars.repository;

import com.academy.mars.entity.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class QuizRepositoryTest {

    @Mock
    private QuizRepository quizRepository;

    private Quiz quiz1;
    private Quiz quiz2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quiz1 = new Quiz();
        quiz1.setId(1L);
        quiz1.setCourseId(1L);
        quiz1.setTitle("Quiz 1");
        quiz2 = new Quiz();
        quiz2.setId(2L);
        quiz2.setCourseId(1L);
        quiz2.setTitle("Quiz 2");
    }

    @Test
    void findByCourseId() {
        when(quizRepository.findByCourseId(1L)).thenReturn(List.of(quiz1, quiz2));
        List<Quiz> quizzes = quizRepository.findByCourseId(1L);
        assertThat(quizzes).hasSize(2);
        assertThat(quizzes).extracting(Quiz::getTitle).containsExactlyInAnyOrder("Quiz 1", "Quiz 2");
        verify(quizRepository, times(1)).findByCourseId(1L);
    }

    @Test
    void findById() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz1));
        Optional<Quiz> result = quizRepository.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Quiz 1");
        verify(quizRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdAndCourseId() {
        when(quizRepository.findByIdAndCourseId(1L, 1L)).thenReturn(Optional.of(quiz1));
        Optional<Quiz> result = quizRepository.findByIdAndCourseId(1L, 1L);
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Quiz 1");
        verify(quizRepository, times(1)).findByIdAndCourseId(1L, 1L);
    }
}
