package com.academy.mars.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LessonsRepository extends JpaRepository<Lessons, String> {

    List<Lessons> findByCourseId(String course_id);

    Optional<Lessons> findById(String id);
}
