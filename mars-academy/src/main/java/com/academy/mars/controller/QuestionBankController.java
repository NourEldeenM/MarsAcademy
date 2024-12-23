package com.academy.mars.controller;

import com.academy.mars.entity.QuestionBank;
import com.academy.mars.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/question-banks")
public class QuestionBankController {

    @Autowired
    private QuestionBankService questionBankService;

    @PostMapping
    public String createQuestionBank(@PathVariable long courseId, @RequestBody QuestionBank questionBank) {
        questionBankService.createQuestionBank(courseId, questionBank);
        return "Question bank created successfully!";
    }

    @GetMapping
    public List<QuestionBank> getAllQuestionBanks(@PathVariable long courseId) {
        return questionBankService.getQuestionBanks(courseId);
    }


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
