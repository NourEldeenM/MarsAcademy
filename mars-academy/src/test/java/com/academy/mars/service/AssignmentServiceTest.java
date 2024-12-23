package com.academy.mars.service;

import com.academy.mars.entity.Assignment;
import com.academy.mars.repository.AssignmentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    //@Mock creates a mock. @InjectMocks creates an instance of the class and injects the mocks that are created with the @Mock annotations into this instance.
    private Assignment assignment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Test Assignment");
        assignment.setDueDate(LocalDate.parse("2024-12-30"));
        assignment.setDescription("Test Description");
    }

    @Test
    void createAssignment() {
        when(assignmentRepository.save(assignment)).thenReturn(assignment);
        assignmentService.createAssignment(assignment);
        verify(assignmentRepository, times(1)).save(assignment);
    }

    @Test
    void getAllAssignmentsByCourseId() {
        long courseId = 1L;
        List<Assignment> assignments = List.of(new Assignment(), new Assignment());
        when(assignmentRepository.findAllByCourseId(courseId)).thenReturn(assignments);
        List<Assignment> result = assignmentService.getAllAssignmentsByCourseId(courseId);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(assignmentRepository, times(1)).findAllByCourseId(courseId);
    }

    @Test
    void getAssignmentByCourseIdAndAssignmentId() {
        long courseId = 1L;
        long assignmentId = 1L;
        when(assignmentRepository.findByCourseIdAndId(courseId, assignmentId))
                .thenReturn(Optional.of(assignment));
        Assignment result = assignmentService.getAssignmentByCourseIdAndAssignmentId(courseId, assignmentId);
        assertNotNull(result);
        assertEquals(assignment.getId(), result.getId());
        verify(assignmentRepository, times(1)).findByCourseIdAndId(courseId, assignmentId);
    }

    @Test
    void updateAssignment() {
        long courseId = 1L;
        long assignmentId = 1L;
        Assignment updatedAssignment = new Assignment();
        updatedAssignment.setTitle("Updated Title");
        updatedAssignment.setDescription("Updated Description");
        updatedAssignment.setDueDate(LocalDate.parse("2025-01-01"));
        when(assignmentRepository.findByCourseIdAndId(courseId, assignmentId))
                .thenReturn(Optional.of(assignment));
        assignmentService.updateAssignment(courseId, assignmentId, updatedAssignment);
        assertEquals("Updated Title", assignment.getTitle());
        assertEquals("Updated Description", assignment.getDescription());
        assertEquals(LocalDate.parse("2025-01-01"), assignment.getDueDate());
        verify(assignmentRepository, times(1)).save(assignment);
    }

    @Test
    void deleteAssignment() {
        long courseId = 1L;
        long assignmentId = 1L;
        when(assignmentRepository.findByCourseIdAndId(courseId, assignmentId))
                .thenReturn(Optional.of(assignment));
        boolean result = assignmentService.deleteAssignment(courseId, assignmentId);
        assertTrue(result);
        verify(assignmentRepository, times(1)).delete(assignment);
    }
}