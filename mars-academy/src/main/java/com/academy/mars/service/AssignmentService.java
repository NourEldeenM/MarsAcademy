package com.academy.mars.service;

import com.academy.mars.entity.Assignment;
import com.academy.mars.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public void createAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
    }

    public List<Assignment> getAllAssignmentsByCourseId(long courseId) {
        return assignmentRepository.findAllByCourseId(courseId);
    }

    public Assignment getAssignmentByCourseIdAndAssignmentId(long courseId, Long assignmentId) {
        return assignmentRepository.findByCourseIdAndId(courseId, assignmentId)
                .orElseThrow(() -> new RuntimeException("assignment not found"));
    }

    public void updateAssignment(long courseId, Long assignmentId, Assignment assignment) {
        Assignment existingAssignment = assignmentRepository.findByCourseIdAndId(courseId, assignmentId)
                .orElseThrow(() -> new RuntimeException("assignment not found"));
        existingAssignment.setTitle(assignment.getTitle());
        existingAssignment.setDescription(assignment.getDescription());
        existingAssignment.setDueDate(assignment.getDueDate());
        assignmentRepository.save(existingAssignment);
    }

    public boolean deleteAssignment(long courseId, Long assignmentId) {
        Assignment assignment = assignmentRepository.findByCourseIdAndId(courseId, assignmentId)
                .orElseThrow(() -> new RuntimeException("assignment not found"));
        assignmentRepository.delete(assignment);
        return true;
    }
}
