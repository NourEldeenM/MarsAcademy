package com.academy.mars.controller;

import com.academy.mars.entity.Question;
import com.academy.mars.entity.Quiz;
import com.academy.mars.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can create quizzes
    @PostMapping
    public String createQuiz(@PathVariable long courseId, @RequestBody Quiz quiz) {
        quiz.setCourseId(courseId);
        quizService.createQuiz(quiz);
        return "Quiz created successfully!";
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can create quizzes (random questions)
    @PostMapping("/{quizId}/randomize")
    public String randomizeQuizQuestions(@PathVariable long courseId, @PathVariable long quizId, @RequestParam int questionCount) {
        quizService.randomizeQuizQuestions(courseId, quizId, questionCount);
        return "Quiz questions randomized successfully!";
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can see quizzes (random questions)
    @GetMapping("/{quizId}/random")
    public List<Question> getRandomQuestions(
            @PathVariable long courseId,
            @PathVariable long quizId,
            @RequestParam int questionCount) {
        return quizService.getRandomQuestionsFromBank(courseId, quizId, questionCount);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can see all quizzes
    @GetMapping
    public List<Quiz> getAllQuizzes(@PathVariable long courseId) {
        return quizService.getAllQuizzes(courseId);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")    // instructors and students can access a quiz
    @GetMapping("/{quizId}")
    public Optional<Quiz> getQuizById(@PathVariable long courseId, @PathVariable long quizId) {
        return quizService.getQuizById(courseId, quizId);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can edit a quiz
    @PatchMapping("/{quizId}")
    public ResponseEntity<String> updateQuiz(
            @PathVariable long courseId,
            @PathVariable long quizId,
            @RequestBody Quiz quiz
    ) {
        quiz.setCourseId(courseId);
        quiz.setId(quizId);
        Quiz updatedQuiz = quizService.updateQuiz(courseId, quizId, quiz);
        if (updatedQuiz != null) {
            return ResponseEntity.ok("Quiz updated successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found for the given course and quiz ID.");
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can delete a quiz
    @DeleteMapping("/{quizId}")
    public String deleteQuiz(@PathVariable long courseId, @PathVariable long quizId) {
        boolean isDeleted = quizService.deleteQuiz(courseId, quizId);
        if (isDeleted) {
            return "Quiz deleted successfully!";
        } else {
            return "Quiz not found!";
        }
    }
}
