package com.academy.mars.service;

import com.academy.mars.entity.CourseInstructors;
import com.academy.mars.entity.Courses;
import com.academy.mars.repository.CoursesRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoursesServices {
    private final CoursesRepository coursesRepository;

    public CoursesServices(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    // Get all courses
    public List<Courses> getCourses() {
        return coursesRepository.findAll();
    }

    // Get course by name (should return only one course)
    public List<Courses> getCourseByName(@NotNull String name) {
        List<Courses> courses = coursesRepository.findByName(name.toLowerCase());
        if (courses.isEmpty()) {
            throw new RuntimeException("there are no course with name  " + name );
        }
        return courses;
    }

    public Courses getCourseById(Long id){
        if(!courseExist(id)){
            throw new RuntimeException("Course Not Found");
        }
        return coursesRepository.findById(id).get();
    }
    @Transactional
    public void deleteCourse(Long id) {
        coursesRepository.deleteById(id);
    }

    @Transactional
    public Courses addCourse(Courses course) {
        coursesRepository.save(course);
        return course;
    }

    @Transactional
    public Courses updateCourse(Courses updatedCourse) {
        if (courseExist(updatedCourse.getId())) {
            Courses existingCourse=coursesRepository.findById(updatedCourse.getId()).get();
            existingCourse.setName(updatedCourse.getName());
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setCategory(updatedCourse.getCategory());
            existingCourse.setDuration(updatedCourse.getDuration());
            coursesRepository.save(existingCourse);
            return updatedCourse;
        }
        throw new RuntimeException("Course with ID " + updatedCourse.getId() + " not found");
    }

    // Get courses by category
    public List<Courses> getCoursesByCategory(String categoryName) {
        return coursesRepository.findByCategory(categoryName);
    }

    // Get courses by title
    public List<Courses> getCoursesByTitle(String categoryTitle) {
        return coursesRepository.findByTitle(categoryTitle);
    }

    public Boolean courseExist(Long courseId){
        return coursesRepository.findById(courseId).isPresent();
    }
}
