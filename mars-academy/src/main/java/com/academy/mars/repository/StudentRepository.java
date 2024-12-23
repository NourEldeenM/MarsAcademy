package com.academy.mars.repository;

import com.academy.mars.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findById(Long id);

    List<Student> findStudentsByCourses_Id(Long courseId);  // Query to find students by course ID
}
