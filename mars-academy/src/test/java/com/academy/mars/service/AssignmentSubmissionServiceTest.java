package com.academy.mars.service;

import com.academy.mars.entity.Assignment;
import com.academy.mars.entity.AssignmentSubmission;
import com.academy.mars.entity.Courses;
import com.academy.mars.entity.Student;
import com.academy.mars.repository.AssignmentSubmissionRepository;
import com.academy.mars.repository.CoursesRepository;
import com.academy.mars.repository.AssignmentRepository;
import com.academy.mars.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AssignmentSubmissionServiceTest {

    @Mock
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Mock
    private CoursesRepository coursesRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private AssignmentSubmissionService assignmentSubmissionService;

    private Assignment assignment;
    private Student student;
    private Courses course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Test Assignment");
        student = new Student();
        student.setId(1L);
        course = new Courses();
        course.setId(1L);
        course.setName("Test Course");
        when(file.getOriginalFilename()).thenReturn("testfile.txt");
        try {
            when(file.getInputStream()).thenReturn(mock(java.io.InputStream.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void submitAssignment() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        String fileLink = "https://drive.google.com/file/d/1Y4xhMGypPGI2sB0uAXgki33DwSpHqsLf/view?usp=drive_link";

        when(file.getOriginalFilename()).thenReturn("testfile.txt");
        AssignmentSubmission result = assignmentSubmissionService.submitAssignment(1L, 1L, fileLink, 1L);
        assertNotNull(result);
        assertEquals(course, result.getCourse());
        assertEquals(assignment, result.getAssignment());
        assertEquals(student, result.getStudent());
        verify(assignmentSubmissionRepository, times(1)).save(result);
    }

    @Test
    void getSubmissionsByStudent() {
        when(assignmentSubmissionRepository.findByStudentId(1L)).thenReturn(List.of(new AssignmentSubmission()));
        List<AssignmentSubmission> result = assignmentSubmissionService.getSubmissionsByStudent(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assignmentSubmissionRepository, times(1)).findByStudentId(1L);
    }

    @Test
    void getSubmissionsByStudentAndCourse() {
        when(assignmentSubmissionRepository.findByStudentIdAndCourseId(1L, 1L)).thenReturn(List.of(new AssignmentSubmission()));
        List<AssignmentSubmission> result = assignmentSubmissionService.getSubmissionsByStudentAndCourse(1L, 1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assignmentSubmissionRepository, times(1)).findByStudentIdAndCourseId(1L, 1L);
    }
}
