package com.academy.mars.service;

import com.academy.mars.entity.CourseEnrollments;
import com.academy.mars.entity.Courses;
import com.academy.mars.entity.Student;
import com.academy.mars.repository.CourseEnrollmentsRepository;
import com.academy.mars.repository.CoursesRepository;
import com.academy.mars.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseEnrollmentsTests {

    @Mock
    private CourseEnrollmentsRepository courseEnrollmentsRepository;

    @Mock
    private CoursesRepository coursesRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private CourseEnrollmentsServices courseEnrollmentsServices;

    private Courses course;
    private Student student;
    private CourseEnrollments enrollment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        course = new Courses();
        course.setId(1L);

        student = new Student();
        student.setId(1L);

        enrollment = new CourseEnrollments(student, course);
    }

    @Test
    public void testEnrollStudentInCourse() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseEnrollmentsRepository.isStudentEnrolledInCourse(student, course)).thenReturn(false);
        when(courseEnrollmentsRepository.save(any(CourseEnrollments.class))).thenReturn(enrollment);

        assertDoesNotThrow(() -> courseEnrollmentsServices.enrollStudentInCourse(1L, 1L));

        verify(courseEnrollmentsRepository, times(1)).save(any(CourseEnrollments.class));
    }

    @Test
    public void testEnrollStudentInCourseAlreadyEnrolled() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseEnrollmentsRepository.isStudentEnrolledInCourse(student, course)).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () ->
                courseEnrollmentsServices.enrollStudentInCourse(1L, 1L));

        assertEquals("Student with ID 1 is already enrolled in course with ID 1", exception.getMessage());
    }

    @Test
    public void testUnEnrollStudentInCourse() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseEnrollmentsRepository.isStudentEnrolledInCourse(student, course)).thenReturn(true);
        when(courseEnrollmentsRepository.findByStudentAndCourse(student, course)).thenReturn(enrollment);

        assertDoesNotThrow(() -> courseEnrollmentsServices.unEnrollStudentInCourse(1L, 1L));

        verify(courseEnrollmentsRepository, times(1)).delete(enrollment);
    }

    @Test
    public void testUnEnrollStudentInCourseNotEnrolled() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseEnrollmentsRepository.isStudentEnrolledInCourse(student, course)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () ->
                courseEnrollmentsServices.unEnrollStudentInCourse(1L, 1L));

        assertEquals("Student with ID 1 is not enrolled in the course with ID 1", exception.getMessage());
    }

    @Test
    public void testGetAllStudentsInCourse() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseEnrollmentsRepository.findByCourse(course)).thenReturn(Arrays.asList(student));

        List<Student> students = courseEnrollmentsServices.getAllStudentsInCourse(1L);

        assertNotNull(students);
        assertEquals(1, students.size());
        assertEquals(student, students.get(0));
    }

    @Test
    public void testFindAllCoursesStudentEnrollIn() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseEnrollmentsRepository.findByStudent(student)).thenReturn(Arrays.asList(course));

        List<Courses> courses = courseEnrollmentsServices.findAllCoursesStudentEnrollIn(1L);

        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals(course, courses.get(0));
    }
}
