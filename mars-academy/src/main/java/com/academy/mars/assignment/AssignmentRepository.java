package com.academy.mars.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllByCourseId(long courseId);
    Optional<Assignment> findByCourseIdAndId(long courseId, Long id);
}
