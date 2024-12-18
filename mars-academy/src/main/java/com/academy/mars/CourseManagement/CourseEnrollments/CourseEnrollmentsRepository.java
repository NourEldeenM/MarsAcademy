package com.academy.mars.CourseManagement.CourseEnrollments;

import com.academy.mars.CourseManagement.Courses.Courses;
import com.academy.mars.UserManagement.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseEnrollmentsRepository extends JpaRepository<CourseEnrollments, Long> {

    @Query("SELECT ce.user FROM CourseEnrollments ce WHERE ce.course = :course")
    List<User> findAllStudentsInACourse(Courses course);

    @Query("SELECT ce.course FROM CourseEnrollments ce WHERE ce.user = :user")
    List<Courses> findAllCoursesOfAStudent(User user);

    @Query("SELECT CASE WHEN COUNT(ce) > 0 THEN TRUE ELSE FALSE END FROM CourseEnrollments ce WHERE ce.user = :user AND ce.course = :course")
    boolean isStudentEnrolledInCourse(User user, Courses course);

    @Query("SELECT ce FROM CourseEnrollments ce WHERE ce.user = :user AND ce.course = :course")
    CourseEnrollments findByUserAndCourse(User user, Courses course);
}
