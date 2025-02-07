package com.academy.mars.questionBank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionBankRepository extends JpaRepository<QuestionBank, String> {
    Optional<QuestionBank> findByIdAndCourseId(String questionBankId, String courseId);

    @Query("SELECT qb FROM QuestionBank qb WHERE qb.course.id = :courseId")
    List<QuestionBank> findByCourseId(String courseId);
}