package com.academy.mars.quiz;


import com.academy.mars.question.Question;
import com.academy.mars.question.QuestionRepository;
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

    public List<Quiz> getAllQuizzes(String courseId) {
        return quizRepository.findByCourseId(courseId);
    }

    public Optional<Quiz> getQuizById(String courseId, String quizId) {
        return quizRepository.findByIdAndCourseId(quizId, courseId);
    }

    public Quiz updateQuiz(String courseId, String quizId, Quiz updatedQuiz) {
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

    public boolean deleteQuiz(String courseId, String quizId) {
        Optional<Quiz> quiz = quizRepository.findByIdAndCourseId(quizId, courseId);
        if (quiz.isPresent()) {
            quizRepository.delete(quiz.get());
            return true;
        }
        return false;
    }


    public void randomizeQuizQuestions(String courseId, String quizId, int questionCount) {
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


    public List<Question> getRandomQuestionsFromBank(String courseId, String quizId, int questionCount) {
        List<Question> allQuestions = questionRepository.findByCourseId(courseId);
        if (allQuestions.size() < questionCount) {
            throw new IllegalArgumentException("Not enough questions in the question bank.");
        }
        Collections.shuffle(allQuestions, new Random());
        return allQuestions.subList(0, questionCount);
    }
}
