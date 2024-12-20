package com.academy.mars.repository.courses;

import com.academy.mars.entity.courses.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoursesRepository extends JpaRepository<Courses, Long> {
    void deleteById(Long id);

    @Query("SELECT c FROM Courses c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Courses> findByName(String name);

    Optional<Courses> findById(Long id);

    @Query("SELECT c FROM Courses c WHERE LOWER(c.category) LIKE LOWER(CONCAT('%', :category, '%'))")
    List<Courses> findByCategory(String category);

    @Query("SELECT c FROM Courses c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Courses> findByTitle(String title);
}
