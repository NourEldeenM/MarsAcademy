package com.academy.mars.service;

import com.academy.mars.entity.Courses;
import com.academy.mars.entity.Lessons;
import com.academy.mars.repository.LessonsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LessonsTests {

    @Mock
    private LessonsRepository lessonsRepository;

    @Mock
    private CoursesServices coursesServices;

    @InjectMocks
    private LessonsServices lessonsServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLesson_Success() {
        Long courseId = 1L;
        Lessons lesson = new Lessons();
        Courses course = new Courses();

        when(coursesServices.courseExist(courseId)).thenReturn(true);
        when(coursesServices.getCourseById(courseId)).thenReturn(course);
        when(lessonsRepository.save(lesson)).thenReturn(lesson);

        Lessons savedLesson = lessonsServices.addLesson(courseId, lesson);

        assertNotNull(savedLesson);
    }

    @Test
    void testAddLesson_CourseNotFound() {
        Long courseId = 1L;
        Lessons lesson = new Lessons();

        when(coursesServices.courseExist(courseId)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> lessonsServices.addLesson(courseId, lesson));

        assertEquals("There is no Course id with id " + courseId, exception.getMessage());
    }

    @Test
    void testGetAllLessons_Success() {
        Long courseId = 1L;
        List<Lessons> lessons = List.of(new Lessons(), new Lessons());

        when(lessonsRepository.findByCourseId(courseId)).thenReturn(lessons);

        List<Lessons> retrievedLessons = lessonsServices.getAllLessons(courseId);

        assertEquals(lessons.size(), retrievedLessons.size());
    }

    @Test
    void testGetLesson_Success() {
        Long lessonId = 1L;
        Lessons lesson = new Lessons();

        when(lessonsRepository.findById(lessonId)).thenReturn(Optional.of(lesson));

        Lessons retrievedLesson = lessonsServices.getLesson(lessonId);

        assertNotNull(retrievedLesson);
    }

    @Test
    void testGetLesson_NotFound() {
        Long lessonId = 1L;

        when(lessonsRepository.findById(lessonId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> lessonsServices.getLesson(lessonId));

        assertEquals("There is no Lesson with id " + lessonId, exception.getMessage());
    }

    @Test
    void testUpdateLesson_Success() {
        Long courseId = 1L;
        Long lessonId = 2L;
        Lessons existingLesson = new Lessons();
        existingLesson.setCourse(new Courses());
        existingLesson.getCourse().setId(courseId);
        Lessons updatedLesson = new Lessons();
        updatedLesson.setTitle("Updated Title");

        when(lessonsRepository.findById(lessonId)).thenReturn(Optional.of(existingLesson));
        when(lessonsRepository.save(existingLesson)).thenReturn(existingLesson);

        Lessons result = lessonsServices.updateLesson(courseId, lessonId, updatedLesson);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void testDeleteLesson_Success() {
        Long courseId = 1L;
        Long lessonId = 2L;
        Lessons lesson = new Lessons();
        lesson.setCourse(new Courses());
        lesson.getCourse().setId(courseId);

        when(lessonsRepository.findById(lessonId)).thenReturn(Optional.of(lesson));

        lessonsServices.deleteLesson(courseId, lessonId);

    }

    @Test
    void testDeleteLesson_NotFound() {
        Long courseId = 1L;
        Long lessonId = 2L;

        when(lessonsRepository.findById(lessonId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> lessonsServices.deleteLesson(courseId, lessonId));

        assertEquals("There is no Lesson with id " + lessonId, exception.getMessage());
    }
}
