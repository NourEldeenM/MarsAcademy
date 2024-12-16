package com.academy.mars.CourseManagement.Courses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoursesRepository extends JpaRepository<Courses,String> {
    void deleteByName(String courseName);

    @Query("SELECT c FROM Courses c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<Courses> findByName(String name);
    @Query("SELECT c FROM Courses c WHERE LOWER(c.category) = LOWER(:category)")
    List<Courses> findByCategory(String category);


}
