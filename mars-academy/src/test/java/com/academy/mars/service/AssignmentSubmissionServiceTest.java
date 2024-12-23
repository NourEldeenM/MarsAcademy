package com.academy.mars.service;

import com.academy.mars.entity.Assignment;
import com.academy.mars.entity.AssignmentSubmission;
import com.academy.mars.entity.Courses;
import com.academy.mars.entity.Student;
import com.academy.mars.repository.AssignmentRepository;
import com.academy.mars.repository.AssignmentSubmissionRepository;
import com.academy.mars.repository.CoursesRepository;
import com.academy.mars.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

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

    @InjectMocks
    private AssignmentSubmissionService assignmentSubmissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void submitAssignment_shouldThrowExceptionWhenFileLinkIsNull() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> assignmentSubmissionService.submitAssignment(1L, 1L, null, 1L));
        assertEquals("File link cannot be null or empty", exception.getMessage());
    }


    @Test
    void submitAssignment_shouldThrowExceptionWhenAssignmentIsNotFound() {
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(new Courses()));
        when(assignmentRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> assignmentSubmissionService.submitAssignment(1L, 1L, "http://example.com", 1L));
        assertEquals("Assignment not found with id 1", exception.getMessage());
    }

    @Test
    void submitAssignment() {
        Courses course = new Courses();
        Assignment assignment = new Assignment();
        Student student = new Student();
//        student.setId(1L);
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(assignmentSubmissionRepository.save(any(AssignmentSubmission.class)))
                .thenReturn(new AssignmentSubmission());
        AssignmentSubmission submission = assignmentSubmissionService.submitAssignment(1L, 1L, "http://example.com", 1L);
        assertNotNull(submission);
        verify(assignmentSubmissionRepository, times(1)).save(any(AssignmentSubmission.class));
    }

    @Test
    void getSubmissionsByStudent() {
        when(assignmentSubmissionRepository.findByStudentId(1L)).thenReturn(List.of(new AssignmentSubmission()));
        List<AssignmentSubmission> submissions = assignmentSubmissionService.getSubmissionsByStudent(1L);
        assertNotNull(submissions);
        assertFalse(submissions.isEmpty());
        assertEquals(1, submissions.size());
        verify(assignmentSubmissionRepository, times(1)).findByStudentId(1L);
    }

    @Test
    void getSubmissionsByStudentAndCourse() {
        when(assignmentSubmissionRepository.findByStudentIdAndCourseId(1L, 1L))
                .thenReturn(List.of(new AssignmentSubmission()));
        List<AssignmentSubmission> submissions = assignmentSubmissionService.getSubmissionsByStudentAndCourse(1L, 1L);
        assertNotNull(submissions);
        assertFalse(submissions.isEmpty());
        verify(assignmentSubmissionRepository, times(1)).findByStudentIdAndCourseId(1L, 1L);
    }
}
