package com.academy.mars.controller;

import com.academy.mars.entity.QuestionBank;
import com.academy.mars.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/question-banks")
public class QuestionBankController {

    @Autowired
    private QuestionBankService questionBankService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can create question banks
    @PostMapping
    public String createQuestionBank(@PathVariable long courseId, @RequestBody QuestionBank questionBank) {
        questionBankService.createQuestionBank(courseId, questionBank);
        return "Question bank created successfully!";
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can access question banks
    @GetMapping
    public List<QuestionBank> getAllQuestionBanks(@PathVariable long courseId) {
        return questionBankService.getQuestionBanks(courseId);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")    // only instructors can delete question banks
    @DeleteMapping("/{questionBankId}")
    public String deleteQuestionBank(@PathVariable long courseId, @PathVariable long questionBankId) {
        List<QuestionBank> questionBanks = questionBankService.getQuestionBankByCourse(courseId);

        Optional<QuestionBank> questionBankToBeDeleted = questionBanks.stream()
                .filter(qb -> qb.getId() == questionBankId)
                .findFirst();
        if (questionBankToBeDeleted.isPresent()) {
            boolean deleted = questionBankService.deleteQuestionBank(questionBankId);
            if (deleted) {
                return "Question bank deleted successfully!";
            }
        }
        throw new RuntimeException("Question bank not found for this course.");
    }
}
