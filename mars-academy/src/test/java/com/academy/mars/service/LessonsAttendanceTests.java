package com.academy.mars.service;

import com.academy.mars.entity.Lessons;
import com.academy.mars.entity.LessonsAttendance;
import com.academy.mars.entity.LessonsOtp;
import com.academy.mars.entity.Student;
import com.academy.mars.repository.LessonsAttendanceRepository;
import com.academy.mars.repository.LessonsOtpRepository;
import com.academy.mars.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LessonsAttendanceTests {

    @Mock
    private LessonsAttendanceRepository lessonsAttendanceRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private LessonsOtpRepository lessonsOtpRepository;

    @InjectMocks
    private LessonsAttendanceServices lessonsAttendanceServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAttendancesByLesson() {
        Lessons lesson = new Lessons();
        List<LessonsAttendance> mockAttendances = List.of(new LessonsAttendance(), new LessonsAttendance());

        when(lessonsAttendanceRepository.findByLesson(lesson)).thenReturn(mockAttendances);

        List<LessonsAttendance> attendances = lessonsAttendanceServices.getAttendancesByLesson(lesson);

        assertNotNull(attendances);
        assertEquals(2, attendances.size());
    }

    @Test
    void testGetAttendancesByStudent() {
        Student student = new Student();
        List<LessonsAttendance> mockAttendances = List.of(new LessonsAttendance(), new LessonsAttendance());

        when(lessonsAttendanceRepository.findByStudent(student)).thenReturn(mockAttendances);

        List<LessonsAttendance> attendances = lessonsAttendanceServices.getAttendancesByStudent(student);

        assertNotNull(attendances);
        assertEquals(2, attendances.size());
    }

    @Test
    void testGetStudentsByLesson() {
        Lessons lesson = new Lessons();
        Student student1 = new Student();
        Student student2 = new Student();
        LessonsAttendance attendance1 = new LessonsAttendance(lesson, student1);
        LessonsAttendance attendance2 = new LessonsAttendance(lesson, student2);

        when(lessonsAttendanceRepository.findByLesson(lesson)).thenReturn(List.of(attendance1, attendance2));

        List<Student> students = lessonsAttendanceServices.getStudentsByLesson(lesson);

        assertNotNull(students);
        assertEquals(2, students.size());
    }

    @Test
    void testGetLessonsByStudent() {
        Student student = new Student();
        Lessons lesson1 = new Lessons();
        Lessons lesson2 = new Lessons();
        LessonsAttendance attendance1 = new LessonsAttendance(lesson1, student);
        LessonsAttendance attendance2 = new LessonsAttendance(lesson2, student);

        when(lessonsAttendanceRepository.findByStudent(student)).thenReturn(List.of(attendance1, attendance2));

        List<Lessons> lessons = lessonsAttendanceServices.getLessonsByStudent(student);

        assertNotNull(lessons);
        assertEquals(2, lessons.size());
    }

    @Test
    void testAddAttendance_Success() {
        Long studentId = 1L;
        String otp = "123456";

        // Create test objects
        Student student = new Student();
        student.setId(studentId);

        Lessons lesson = new Lessons();
        lesson.setId(101L);

        LessonsOtp lessonsOtp = new LessonsOtp();
        lessonsOtp.setLesson(lesson);

        LessonsAttendance expectedAttendance = new LessonsAttendance(lesson, student);

        // Mock repository methods
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(lessonsOtpRepository.findByOtp(otp)).thenReturn(Optional.of(lessonsOtp));
        when(lessonsAttendanceRepository.save(any(LessonsAttendance.class))).thenReturn(expectedAttendance);

        // Call the service method
        LessonsAttendance actualAttendance = lessonsAttendanceServices.addAttendance(studentId, otp);

        // Assertions
        assertNotNull(actualAttendance);
        assertEquals(student, actualAttendance.getStudent());
        assertEquals(lesson, actualAttendance.getLesson());
    }


    @Test
    void testAddAttendance_StudentNotFound() {
        Long studentId = 1L;
        String otp = "123456";

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> lessonsAttendanceServices.addAttendance(studentId, otp));

        assertEquals("Student not found", exception.getMessage());
    }

    @Test
    void testAddAttendance_InvalidOtp() {
        Long studentId = 1L;
        String otp = "123456";
        Student student = new Student();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(lessonsOtpRepository.findByOtp(otp)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> lessonsAttendanceServices.addAttendance(studentId, otp));

        assertEquals("Otp is wrong", exception.getMessage());
    }
}
