package com.academy.mars.repository;

import com.academy.mars.entity.CourseEnrollments;
import com.academy.mars.entity.Student;
import com.academy.mars.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseEnrollmentsRepository extends JpaRepository<CourseEnrollments, Long> {

    @Query("SELECT ce.student FROM CourseEnrollments ce WHERE ce.course = :course")
    List<Student> findByCourse(Courses course);

    @Query("SELECT ce.course FROM CourseEnrollments ce WHERE ce.student = :student")
    List<Courses> findByStudent(Student student);

    @Query("SELECT CASE WHEN COUNT(ce) > 0 THEN TRUE ELSE FALSE END FROM CourseEnrollments ce WHERE ce.student = :student AND ce.course = :course")
    boolean isStudentEnrolledInCourse(Student student, Courses course);

    @Query("SELECT ce FROM CourseEnrollments ce WHERE ce.student = :student AND ce.course = :course")
    CourseEnrollments findByStudentAndCourse(Student student, Courses course);
}
