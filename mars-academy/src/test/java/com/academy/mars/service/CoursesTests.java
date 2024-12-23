package com.academy.mars.service;

import com.academy.mars.entity.Courses;
import com.academy.mars.repository.CoursesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CoursesTests {

    @Mock
    private CoursesRepository coursesRepository;

    @InjectMocks
    private CoursesServices coursesServices;

    private Courses course;

    @BeforeEach
    public void setUp() {
        course = new Courses();
        course.setId(1L);
        course.setName("Java");
        course.setTitle("Java Programming");
        course.setDescription("Learn Java");
        course.setCategory("Programming");
        course.setDuration(120);
    }

    @Test
    public void testGetCourses() {
        when(coursesRepository.findAll()).thenReturn(Arrays.asList(course));

        assertEquals(1, coursesServices.getCourses().size());
    }

    @Test
    public void testGetCourseByName_Found() {
        when(coursesRepository.findByName("java")).thenReturn(Arrays.asList(course));

        var result = coursesServices.getCourseByName("Java");
        assertEquals(1, result.size());
        assertEquals(course, result.get(0));
    }

    @Test
    public void testGetCourseByName_NotFound() {
        when(coursesRepository.findByName("java")).thenReturn(Arrays.asList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            coursesServices.getCourseByName("Java");
        });

        assertEquals("there are no course with name  Java", exception.getMessage());
    }

    @Test
    public void testGetCourseById_Found() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));

        Courses result = coursesServices.getCourseById(1L);
        assertEquals(course, result);
    }

    @Test
    public void testGetCourseById_NotFound() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            coursesServices.getCourseById(1L);
        });

        assertEquals("Course Not Found", exception.getMessage());
    }

    @Test
    public void testAddCourse() {
        when(coursesRepository.save(course)).thenReturn(course);

        Courses result = coursesServices.addCourse(course);
        assertEquals(course, result);
    }

    @Test
    public void testDeleteCourse() {
        doNothing().when(coursesRepository).deleteById(1L);

        coursesServices.deleteCourse(1L);
    }

    @Test
    public void testUpdateCourse_Found() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(coursesRepository.save(course)).thenReturn(course);

        course.setName("Updated Java");
        Courses result = coursesServices.updateCourse(course);
        assertEquals("Updated Java", result.getName());
    }

    @Test
    public void testUpdateCourse_NotFound() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            coursesServices.updateCourse(course);
        });

        assertEquals("Course with ID 1 not found", exception.getMessage());
    }

    @Test
    public void testGetCoursesByCategory() {
        when(coursesRepository.findByCategory("Programming")).thenReturn(Arrays.asList(course));

        var result = coursesServices.getCoursesByCategory("Programming");
        assertEquals(1, result.size());
        assertEquals(course, result.get(0));
    }

    @Test
    public void testGetCoursesByTitle() {
        when(coursesRepository.findByTitle("Java Programming")).thenReturn(Arrays.asList(course));

        var result = coursesServices.getCoursesByTitle("Java Programming");
        assertEquals(1, result.size());
        assertEquals(course, result.get(0));
    }
}
