package com.academy.mars.repository;

import com.academy.mars.entity.LessonsFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonsFilesRepository extends JpaRepository<LessonsFiles, Long> {

    // Get all files associated with a specific lesson
    List<LessonsFiles> findByLessonId(Long lessonId);

    // Get file by its ID
    Optional<LessonsFiles> findById(Long id);
}
