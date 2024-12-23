package com.academy.mars.controller;


import com.academy.mars.entity.Assignment;
import com.academy.mars.entity.Question;
import com.academy.mars.entity.QuestionBank;
import com.academy.mars.entity.Quiz;
import com.academy.mars.repository.AssignmentRepository;
import com.academy.mars.repository.QuestionRepository;
import com.academy.mars.repository.QuizRepository;
import com.academy.mars.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/question-banks/{questionBankId}/questions")
public class QuestionController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private QuestionRepository questionRepository;


    @Autowired
    private QuestionService questionService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can create questions
    @PostMapping("/quiz/{quizId}")
    public ResponseEntity<Question> createQuestionForQuiz(
            @PathVariable long courseId,
            @PathVariable long questionBankId,
            @PathVariable long quizId,
            @RequestBody Question question) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found with id: " + quizId));
        question.setQuiz(quiz);
        question.setQuestionBank(new QuestionBank());
        Question savedQuestion = questionRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can create questions
    @PostMapping("/assignment/{assignmentId}")
    public ResponseEntity<Question> createQuestionForAssignment(
            @PathVariable long courseId,
            @PathVariable long questionBankId,
            @PathVariable long assignmentId,
            @RequestBody Question question) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with id: " + assignmentId));
        question.setAssignment(assignment);
        question.setQuestionBank(new QuestionBank());
        Question savedQuestion = questionRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can get all questions for quiz / assessment
    @GetMapping
    public List<Question> getAllQuestions(
            @PathVariable long courseId,
            @PathVariable long questionBankId,
            @RequestParam(required = false) Long quizId,
            @RequestParam(required = false) Long assignmentId) {
        if (quizId != null) {
            return questionService.getQuestionsByQuiz(quizId);
        } else if (assignmentId != null) {
            return questionService.getQuestionsByAssignment(assignmentId);
        }
        return questionService.getQuestionsByBank(questionBankId);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can edit questions
    @PatchMapping("/{questionId}")
    public String updateQuestion(@PathVariable long courseId, @PathVariable long questionBankId,
                                 @PathVariable long questionId, @RequestBody Question question) {
        questionService.updateQuestion(questionId, question);
        return "Question updated successfully!";
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can delete questions
    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable long courseId, @PathVariable long questionBankId,
                                 @PathVariable long questionId) {
        boolean deleted = questionService.deleteQuestion(questionId);
        if (deleted) {
            return "Question deleted successfully!";
        } else {
            throw new RuntimeException("Question not found");
        }
    }
}