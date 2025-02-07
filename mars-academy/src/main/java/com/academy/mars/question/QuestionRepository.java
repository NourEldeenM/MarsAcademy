package com.academy.mars.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> findByQuizId(String quizId);

    boolean existsById(String id);

    void deleteById(String id);

    List<Question> findByCourseId(String courseId);


    List<Question> findByQuestionBankId(String questionBankId);


    List<Question> findByAssignmentId(String assignmentId);
}
