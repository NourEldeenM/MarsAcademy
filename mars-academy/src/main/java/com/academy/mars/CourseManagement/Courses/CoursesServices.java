package com.academy.mars.CourseManagement.Courses;

import jakarta.transaction.Transactional;
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
    public List<Courses> getCourseByName(String name) {
        List<Courses> courses = coursesRepository.findByName(name.toLowerCase());
        if (courses.isEmpty()) {
            throw new RuntimeException("there are no course with name  " + name );
        }
        return courses;
    }

    public Optional<Courses> getCourseById(Long id){
        return coursesRepository.findById(id);
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
        Optional<Courses> existingCourse = coursesRepository.findById(updatedCourse.getId());

        if (existingCourse.isPresent()) {
            Courses courseToUpdate = existingCourse.get();

            courseToUpdate.setName(updatedCourse.getName());
            courseToUpdate.setTitle(updatedCourse.getTitle());
            courseToUpdate.setDescription(updatedCourse.getDescription());
            courseToUpdate.setCategory(updatedCourse.getCategory());
            courseToUpdate.setDuration(updatedCourse.getDuration());

            coursesRepository.save(courseToUpdate);
            return courseToUpdate;
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
}
