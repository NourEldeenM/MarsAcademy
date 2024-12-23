package com.academy.mars.service;

import com.academy.mars.entity.Courses;
import com.academy.mars.entity.QuestionBank;
import com.academy.mars.repository.CoursesRepository;
import com.academy.mars.repository.QuestionBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionBankService {

    @Autowired
    private QuestionBankRepository questionBankRepository;
    @Autowired
    private CoursesRepository courseRepository;

    public void createQuestionBank(long courseId, QuestionBank questionBank) {
        Courses course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        questionBank.setCourse(course);
        questionBankRepository.save(questionBank);
    }

    public List<QuestionBank> getQuestionBanks(long courseId) {
        return questionBankRepository.findByCourseId(courseId);
    }

    public boolean deleteQuestionBank(long questionBankId) {
        if (questionBankRepository.existsById(questionBankId)) {
            questionBankRepository.deleteById(questionBankId);
            return true;
        }
        return false;
    }

    public List<QuestionBank> getQuestionBankByCourse(long courseId) {
        return questionBankRepository.findByCourseId(courseId);
    }
}
