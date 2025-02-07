package com.academy.mars.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {
    List<Assignment> findAllByCourseId(String courseId);
    Optional<Assignment> findByCourseIdAndId(String courseId, String id);
}
