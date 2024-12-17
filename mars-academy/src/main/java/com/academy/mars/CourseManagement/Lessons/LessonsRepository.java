package com.academy.mars.CourseManagement.Lessons;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LessonsRepository extends JpaRepository<Lessons, Long> {

    List<Lessons> findByCourseId(Long course_id);

    Optional<Lessons> findById(Long id);
}
