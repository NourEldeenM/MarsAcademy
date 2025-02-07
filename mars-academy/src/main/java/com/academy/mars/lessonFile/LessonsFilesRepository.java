package com.academy.mars.lessonFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonsFilesRepository extends JpaRepository<LessonsFiles, String> {

    // Get all files associated with a specific lesson
    List<LessonsFiles> findByLessonId(String lessonId);

    // Get file by its ID
    Optional<LessonsFiles> findById(String id);
}
