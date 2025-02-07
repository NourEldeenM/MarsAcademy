package com.academy.mars.student;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    @NotNull Optional<Student> findById(@NotNull String id);

    List<Student> findStudentsByCourses_Id(String courseId);  // Query to find students by course ID
}
