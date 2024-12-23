package com.academy.mars.service;


import com.academy.mars.entity.Question;
import com.academy.mars.entity.Quiz;
import com.academy.mars.repository.QuestionRepository;
import com.academy.mars.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    public void createQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public List<Quiz> getAllQuizzes(long courseId) {
        return quizRepository.findByCourseId(courseId);
    }

    public Optional<Quiz> getQuizById(long courseId, long quizId) {
        return quizRepository.findByIdAndCourseId(quizId, courseId);
    }

    public Quiz updateQuiz(long courseId, long quizId, Quiz updatedQuiz) {
        Optional<Quiz> existingQuiz = quizRepository.findByIdAndCourseId(quizId, courseId);

        if (existingQuiz.isPresent()) {
            Quiz quiz = existingQuiz.get();
            quiz.setTitle(updatedQuiz.getTitle());
            quiz.setDescription(updatedQuiz.getDescription());
            quiz.setStartTime(updatedQuiz.getStartTime());
            quiz.setEndTime(updatedQuiz.getEndTime());
            return quizRepository.save(quiz);
        }
        return null;
    }

    public boolean deleteQuiz(long courseId, long quizId) {
        Optional<Quiz> quiz = quizRepository.findByIdAndCourseId(quizId, courseId);
        if (quiz.isPresent()) {
            quizRepository.delete(quiz.get());
            return true;
        }
        return false;
    }


    public void randomizeQuizQuestions(long courseId, long quizId, int questionCount) {
        List<Question> allQuestions = questionRepository.findByCourseId(courseId);
        if (allQuestions.size() < questionCount) {
            throw new IllegalArgumentException("Not enough questions in the question bank.");
        }
        Collections.shuffle(allQuestions, new Random());
        List<Question> randomizedQuestions = allQuestions.subList(0, questionCount);
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        quiz.setQuestions(randomizedQuestions);
        quizRepository.save(quiz);
    }


    public List<Question> getRandomQuestionsFromBank(long courseId, long quizId, int questionCount) {
        List<Question> allQuestions = questionRepository.findByCourseId(courseId);
        if (allQuestions.size() < questionCount) {
            throw new IllegalArgumentException("Not enough questions in the question bank.");
        }
        Collections.shuffle(allQuestions, new Random());
        return allQuestions.subList(0, questionCount);
    }
}
