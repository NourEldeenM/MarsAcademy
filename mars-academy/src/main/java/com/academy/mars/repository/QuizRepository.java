package com.academy.mars.repository;

import com.academy.mars.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCourseId(long courseId);
    Optional<Quiz> findById(long quizId);
    Optional<Quiz> findByIdAndCourseId(long quizId, long courseId);
}
