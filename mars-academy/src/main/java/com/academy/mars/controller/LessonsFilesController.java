package com.academy.mars.controller;

import com.academy.mars.entity.LessonsFiles;
import com.academy.mars.service.LessonsFilesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/files")
public class LessonsFilesController {

    @Autowired
    private LessonsFilesServices lessonsFilesServices;

    @PostMapping("/upload/{lessonId}")
    public ResponseEntity<LessonsFiles> uploadFile(@PathVariable Long lessonId, @RequestParam("file") MultipartFile file) {
        try {
            LessonsFiles uploadedFile = lessonsFilesServices.uploadFile(lessonId, file);
            return new ResponseEntity<>(uploadedFile, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<List<LessonsFiles>> getAllFilesByLessonId(@PathVariable Long lessonId) {
        List<LessonsFiles> files = lessonsFilesServices.getAllFilesByLessonId(lessonId);
        if (files.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<LessonsFiles> getFileById(@PathVariable Long fileId) {
        Optional<LessonsFiles> file = lessonsFilesServices.getFileById(fileId);
        return file.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFileById(@PathVariable Long fileId) {
        lessonsFilesServices.deleteFileById(fileId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<Void> deleteAllFilesByLessonId(@PathVariable Long lessonId) {
        lessonsFilesServices.deleteAllFilesByLessonId(lessonId);
        return ResponseEntity.noContent().build();
    }
}
