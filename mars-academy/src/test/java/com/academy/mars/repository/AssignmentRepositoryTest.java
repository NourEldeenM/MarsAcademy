package com.academy.mars.repository;

import com.academy.mars.assignment.Assignment;
import com.academy.mars.assignment.AssignmentRepository;
import com.academy.mars.assignment.AssignmentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    public AssignmentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllByCourseId() {
        String courseId = "";
        Assignment assignment1 = new Assignment();
        assignment1.setCourseId(courseId);
        assignment1.setTitle("Assignment 1");

        Assignment assignment2 = new Assignment();
        assignment2.setCourseId(courseId);
        assignment2.setTitle("Assignment 2");

        when(assignmentRepository.findAllByCourseId(courseId)).thenReturn(Arrays.asList(assignment1, assignment2));
        List<Assignment> assignments = assignmentService.getAllAssignmentsByCourseId(courseId);

        assertThat(assignments).hasSize(2);
        assertThat(assignments).extracting(Assignment::getTitle)
                .containsExactlyInAnyOrder("Assignment 1", "Assignment 2");

        verify(assignmentRepository, times(1)).findAllByCourseId(courseId);
    }

    @Test
    void findByCourseIdAndId() {
        long courseId = 1L;
        long assignmentId = 1L;
        Assignment assignment = new Assignment();
        assignment.setCourseId(courseId);
        assignment.setTitle("Assignment 1");
        assignment.setId(assignmentId);
        when(assignmentRepository.findByCourseIdAndId(courseId, assignmentId)).thenReturn(Optional.of(assignment));
        Optional<Assignment> result = Optional.ofNullable(assignmentService.getAssignmentByCourseIdAndAssignmentId(courseId, assignmentId));
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Assignment 1");
        verify(assignmentRepository, times(1)).findByCourseIdAndId(courseId, assignmentId);
    }
}
