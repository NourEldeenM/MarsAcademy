package com.academy.mars.grade;

import com.academy.mars.course.Courses;
import com.academy.mars.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, String> {
    List<Grade> findByCourseId(String courseId);

    Optional<Grade> findByStudentIdAndQuizId(String studentId, String quizId);

    Optional<Grade> findByStudentIdAndAssignmentId(String studentId, String assignmentId);

    void deleteByStudentIdAndQuizId(String studentId, String quizId);

    void deleteByStudentIdAndAssignmentId(String studentId, String assignmentId);

    @Query("SELECT g FROM Grade g WHERE g.student = :student AND g.course = :course")
    List<Grade> findByStudentAndCourse(Student student, Optional<Courses> course);

}
