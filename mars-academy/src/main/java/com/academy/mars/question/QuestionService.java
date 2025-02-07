package com.academy.mars.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getQuestionsByBank(String questionBankId) {
        return questionRepository.findByQuestionBankId(questionBankId);
    }

    public void updateQuestion(String questionId, Question question) {
        question.setId(questionId);
        questionRepository.save(question);
    }

    public boolean deleteQuestion(String questionId) {
        if (questionRepository.existsById(questionId)) {
            questionRepository.deleteById(questionId);
            return true;
        }
        return false;
    }

    public List<Question> getQuestionsByQuiz(String quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    public List<Question> getQuestionsByAssignment(String assignmentId) {
        return questionRepository.findByAssignmentId(assignmentId);
    }
}