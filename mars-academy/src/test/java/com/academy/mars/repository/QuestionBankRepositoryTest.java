package com.academy.mars.repository;

import com.academy.mars.entity.Courses;
import com.academy.mars.entity.QuestionBank;
import com.academy.mars.service.QuestionBankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class QuestionBankRepositoryTest {

    @Mock
    private QuestionBankRepository questionBankRepository;

    @InjectMocks
    private QuestionBankService questionBankService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdAndCourseId() {
        Long courseId = 1L;
        Long questionBankId = 1L;
        Courses course = new Courses();
        course.setId(courseId);
        QuestionBank questionBank = new QuestionBank();
        questionBank.setId(questionBankId);
        questionBank.setCourse(course);
        when(questionBankRepository.findByIdAndCourseId(questionBankId, courseId)).thenReturn(Optional.of((questionBank)));
        Optional<QuestionBank> result = questionBankRepository.findByIdAndCourseId(questionBankId, courseId);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(questionBank);

        verify(questionBankRepository, times(1)).findByIdAndCourseId(questionBankId, courseId);
    }

    @Test
    void findByCourseId() {
        Long courseId = 1L;
        Courses course = new Courses();
        course.setId(courseId);
        QuestionBank questionBank1 = new QuestionBank();
        questionBank1.setCourse(course);
        QuestionBank questionBank2 = new QuestionBank();
        questionBank2.setCourse(course);
        when(questionBankRepository.findByCourseId(courseId)).thenReturn(Arrays.asList(questionBank1, questionBank2));
        List<QuestionBank> result = questionBankRepository.findByCourseId(courseId);
        assertThat(result).hasSize(2);
        assertThat(result).extracting(QuestionBank::getCourse).containsOnly(course);

        verify(questionBankRepository, times(1)).findByCourseId(courseId);
    }
}
