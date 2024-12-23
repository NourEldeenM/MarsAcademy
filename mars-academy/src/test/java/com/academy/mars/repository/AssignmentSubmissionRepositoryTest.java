package com.academy.mars.repository;

import com.academy.mars.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class AssignmentSubmissionRepositoryTest {

    @Mock
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Mock
    private User user;

    @Mock
    private Student student;

    @Mock
    private Courses course1;

    @Mock
    private Assignment assignment;

    private AssignmentSubmission submission1;
    private AssignmentSubmission submission2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("testUser@example.com");
        user.setRole(UserRole.valueOf("ROLE_STUDENT"));
        student = new Student();
        student.setUser(user);
        course1 = new Courses();
        assignment = new Assignment();
        submission1 = new AssignmentSubmission();
        submission1.setStudent(student);
        submission1.setCourse(course1);
        submission1.setAssignment(assignment);
        submission1.setFileLink("path1");
        submission1.setSubmissionTime(LocalDateTime.now());
        submission2 = new AssignmentSubmission();
        submission2.setStudent(student);
        submission2.setCourse(course1);
        submission2.setAssignment(assignment);
        submission2.setFileLink("path2");
        submission2.setSubmissionTime(LocalDateTime.now());
    }

    @Test
    void findByStudentId() {
        when(assignmentSubmissionRepository.findByStudentId(student.getId())).thenReturn(List.of(submission1, submission2));
        List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findByStudentId(student.getId());
        assertThat(submissions).hasSize(2);
        assertThat(submissions).extracting(AssignmentSubmission::getFileLink).containsExactlyInAnyOrder("path1", "path2");
        verify(assignmentSubmissionRepository, times(1)).findByStudentId(student.getId());
    }

    @Test
    void findByStudentIdAndCourseId() {
        when(assignmentSubmissionRepository.findByStudentIdAndCourseId(student.getId(), course1.getId())).thenReturn(List.of(submission1));
        List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findByStudentIdAndCourseId(student.getId(), course1.getId());
        assertThat(submissions).hasSize(1);
        assertThat(submissions.getFirst().getFileLink()).isEqualTo("path1");
        verify(assignmentSubmissionRepository, times(1)).findByStudentIdAndCourseId(student.getId(), course1.getId());
    }
}
