package com.academy.mars.service;

import com.academy.mars.entity.Courses;
import com.academy.mars.entity.Quiz;
import com.academy.mars.entity.Question;
import com.academy.mars.repository.QuizRepository;
import com.academy.mars.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;
    private Question question;
    private Courses course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        quiz = new Quiz();
        course = new Courses();
        course.setId(1L);
        quiz.setId(1L);
        quiz.setCourseId(1L);
        quiz.setTitle("Sample Quiz");
        quiz.setDescription("This is a sample quiz.");
        question = new Question();
        question.setId(1L);
        question.setDescription("Sample Question");
        when(quizRepository.findByCourseId(1L)).thenReturn(List.of(quiz));
        when(questionRepository.findByCourseId(1L)).thenReturn(List.of(question));
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
    }

    @Test
    void createQuiz() {
        quizService.createQuiz(quiz);
        verify(quizRepository, times(1)).save(quiz);
    }

    @Test
    void getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes(1L);
        assertNotNull(quizzes);
        assertFalse(quizzes.isEmpty());
        assertEquals(1, quizzes.size());
    }

    @Test
    void getQuizById() {
        Optional<Quiz> foundQuiz = quizService.getQuizById(1L, 1000L);
        assertFalse(foundQuiz.isPresent());
    }

    @Test
    void deleteQuiz() {
        Quiz quizToBeDeleted = new Quiz();
        quizToBeDeleted.setId(2L);
        quizToBeDeleted.setCourseId(1L);
        when(quizRepository.findByIdAndCourseId(2L, 1L)).thenReturn(Optional.of(quizToBeDeleted));
        boolean deleted = quizService.deleteQuiz(1L, 2L);
        assertTrue(deleted);
        verify(quizRepository, times(1)).delete(quizToBeDeleted);
    }


    @Test
    void randomizeQuizQuestions() {
        when(questionRepository.findByCourseId(1L)).thenReturn(createSampleQuestions(10));
        quizService.randomizeQuizQuestions(1L, 1L, 5);
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }


    private List<Question> createSampleQuestions(int count) {
        List<Question> questions = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Question question = new Question();
            question.setId((long) i);
            question.setDescription("Sample Question " + i);
            questions.add(question);
        }
        return questions;
    }
}
