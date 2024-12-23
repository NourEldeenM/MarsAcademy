package com.academy.mars.service;

import com.academy.mars.entity.Question;
import com.academy.mars.entity.Quiz;
import com.academy.mars.repository.QuestionRepository;
import com.academy.mars.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public QuestionService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public void createQuestion(Question question) {
        Quiz quiz = quizRepository.findById(question.getQuiz().getId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        question.setQuiz(quiz);
        questionRepository.save(question);
    }


    public List<Question> getAllQuestions(long quizId) {
        return questionRepository.findByQuizId(quizId);
    }


    public List<Question> getQuestionsByBank(long questionBankId) {
        return questionRepository.findByQuestionBankId(questionBankId);
    }

    public void updateQuestion(long questionId, Question question) {
        question.setId(questionId);
        questionRepository.save(question);
    }

    public boolean deleteQuestion(long questionId) {
        if (questionRepository.existsById(questionId)) {
            questionRepository.deleteById(questionId);
            return true;
        }
        return false;
    }

    public List<Question> getQuestionsByQuiz(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    public List<Question> getQuestionsByAssignment(Long assignmentId) {
        return questionRepository.findByAssignmentId(assignmentId);
    }
}