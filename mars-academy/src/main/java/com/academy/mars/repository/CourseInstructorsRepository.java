package com.academy.mars.repository;

import com.academy.mars.entity.CourseInstructors;
import com.academy.mars.entity.Courses;
import com.academy.mars.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseInstructorsRepository extends JpaRepository<CourseInstructors, Long> {

    // Find all instructors for a given course
    List<CourseInstructors> findByCourse(Courses course);

    // Find all courses for a given instructor
    List<CourseInstructors> findByInstructor(Instructor instructor);

    // Find specific CourseInstructor
    Optional<CourseInstructors> findByCourseAndInstructor(Courses course, Instructor instructor);

    // Delete all instructors for a given course
    void deleteByCourse(Courses course);

    // Delete specific instructor from a course
    void deleteByCourseAndInstructor(Courses course, Instructor instructor);
}
