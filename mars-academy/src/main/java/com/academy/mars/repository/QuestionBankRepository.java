package com.academy.mars.repository;

import com.academy.mars.entity.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionBankRepository extends JpaRepository<QuestionBank, Long> {
    Optional<QuestionBank> findByIdAndCourseId(Long questionBankId, Long courseId);

    @Query("SELECT qb FROM QuestionBank qb WHERE qb.course.id = :courseId")
    List<QuestionBank> findByCourseId(Long courseId);
}