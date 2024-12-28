package com.academy.mars.service;

import com.academy.mars.entity.LessonsFiles;
import com.academy.mars.repository.LessonsFilesRepository;
import com.academy.mars.entity.Lessons;
import com.academy.mars.repository.LessonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class LessonsFilesServices {

    @Autowired
    private LessonsFilesRepository lessonsFilesRepository;

    @Autowired
    private LessonsRepository lessonRepository;


    public LessonsFiles uploadFile(Long lessonId, MultipartFile file) throws IOException {
        // Retrieve the lesson by its ID
        Lessons lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        // Create a LessonsFiles entity
        LessonsFiles lessonsFiles = new LessonsFiles(
                lesson,
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
        );

        // Save the file to the repository and return the saved entity
        return lessonsFilesRepository.save(lessonsFiles);
    }

    public List<LessonsFiles> getAllFilesByLessonId(Long lessonId) {
        return lessonsFilesRepository.findByLessonId(lessonId);
    }

    public Optional<LessonsFiles> getFileById(Long fileId) {
        return lessonsFilesRepository.findById(fileId);
    }

    public void deleteFileById(Long fileId) {
        lessonsFilesRepository.deleteById(fileId);
    }

    public void deleteAllFilesByLessonId(Long lessonId) {
        List<LessonsFiles> files = lessonsFilesRepository.findByLessonId(lessonId);
        lessonsFilesRepository.deleteAll(files);
    }
}
