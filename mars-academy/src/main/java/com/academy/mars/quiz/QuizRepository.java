package com.academy.mars.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
    List<Quiz> findByCourseId(String courseId);
    Optional<Quiz> findById(String quizId);
    Optional<Quiz> findByIdAndCourseId(String quizId, String courseId);
}
