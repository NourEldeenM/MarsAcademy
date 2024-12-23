package com.academy.mars.repository;

import com.academy.mars.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByCourseId(long courseId);

    Optional<Grade> findByStudentIdAndQuizId(long studentId, long quizId);

    Optional<Grade> findByStudentIdAndAssignmentId(long studentId, long assignmentId);

    void deleteByStudentIdAndQuizId(long studentId, long quizId);

    void deleteByStudentIdAndAssignmentId(long studentId, long assignmentId);
}
