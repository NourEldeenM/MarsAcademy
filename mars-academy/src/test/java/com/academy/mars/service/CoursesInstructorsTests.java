package com.academy.mars.service;

import com.academy.mars.entity.CourseInstructors;
import com.academy.mars.entity.Courses;
import com.academy.mars.entity.Instructor;
import com.academy.mars.repository.CourseInstructorsRepository;
import com.academy.mars.repository.CoursesRepository;
import com.academy.mars.repository.InstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CoursesInstructorsTests {

    @Mock
    private CourseInstructorsRepository courseInstructorsRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private CoursesRepository coursesRepository;

    @InjectMocks
    private CourseInstructorsServices courseInstructorsServices;

    private Courses course;
    private Instructor instructor;
    private CourseInstructors courseInstructor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        course = new Courses();
        course.setId(1L);

        instructor = new Instructor();
        instructor.setId(1L);

        courseInstructor = new CourseInstructors(course, instructor);
    }

    @Test
    public void testAddInstructorToCourse() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseInstructorsRepository.save(any(CourseInstructors.class))).thenReturn(courseInstructor);

        CourseInstructors result = courseInstructorsServices.addInstructorToCourse(1L, 1L);

        assertNotNull(result);
        assertEquals(course, result.getCourse());
        assertEquals(instructor, result.getInstructor());
    }

    @Test
    public void testGetInstructorsByCourse() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseInstructorsRepository.findByCourse(course)).thenReturn(Arrays.asList(courseInstructor));

        List<CourseInstructors> result = courseInstructorsServices.getInstructorsByCourse(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(courseInstructor, result.get(0));
    }

    @Test
    public void testGetCoursesByInstructor() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseInstructorsRepository.findByInstructor(instructor)).thenReturn(Arrays.asList(courseInstructor));

        List<CourseInstructors> result = courseInstructorsServices.getCoursesByInstructor(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(courseInstructor, result.get(0));
    }

    @Test
    public void testRemoveInstructorFromCourse() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        assertDoesNotThrow(() -> courseInstructorsServices.removeInstructorFromCourse(1L, 1L));
    }

    @Test
    public void testRemoveAllInstructorsFromCourse() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));

        assertDoesNotThrow(() -> courseInstructorsServices.removeAllInstructorsFromCourse(1L));
    }

    @Test
    public void testIsACourseInstructor() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseInstructorsRepository.findByCourseAndInstructor(course, instructor)).thenReturn(Optional.of(courseInstructor));

        boolean result = courseInstructorsServices.isACourseInstructor(1L, 1L);

        assertTrue(result);
    }

    @Test
    public void testCourseDoesNotExist() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseInstructorsServices.addInstructorToCourse(1L, 1L));
        assertEquals("Course with ID 1 not found", exception.getMessage());
    }

    @Test
    public void testInstructorDoesNotExist() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseInstructorsServices.addInstructorToCourse(1L, 1L));
        assertEquals("Instructor with ID 1 not found", exception.getMessage());
    }
}
