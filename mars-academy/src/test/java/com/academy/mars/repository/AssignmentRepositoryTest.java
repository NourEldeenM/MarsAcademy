package com.academy.mars.repository;

import com.academy.mars.entity.Assignment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AssignmentRepositoryTest {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Test
    void findAllByCourseId() {
        long courseId = 1L;
        Assignment assignment1 = new Assignment();
        assignment1.setCourseId(courseId);
        assignment1.setTitle("Assignment 1");
        Assignment assignment2 = new Assignment();
        assignment2.setCourseId(courseId);
        assignment2.setTitle("Assignment 2");
        Assignment assignment3 = new Assignment();
        assignment3.setCourseId(2L);
        assignment3.setTitle("Assignment 3");
        assignmentRepository.save(assignment1);
        assignmentRepository.save(assignment2);
        assignmentRepository.save(assignment3);
        List<Assignment> assignments = assignmentRepository.findAllByCourseId(courseId);
        assertThat(assignments).hasSize(2);
        assertThat(assignments).extracting(Assignment::getTitle)
                .containsExactlyInAnyOrder("Assignment 1", "Assignment 2");
    }

    @Test
    void findByCourseIdAndId() {
        long courseId = 1L;
        Assignment assignment1 = new Assignment();
        assignment1.setCourseId(courseId);
        assignment1.setTitle("Assignment 1");
        Assignment assignment2 = new Assignment();
        assignment2.setCourseId(courseId);
        assignment2.setTitle("Assignment 2");
        assignmentRepository.save(assignment1);
        assignmentRepository.save(assignment2);
        Optional<Assignment> result = assignmentRepository.findByCourseIdAndId(courseId, assignment1.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Assignment 1");
    }
}
