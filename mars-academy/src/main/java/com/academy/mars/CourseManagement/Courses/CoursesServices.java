package com.academy.mars.CourseManagement.Courses;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class CoursesServices {
    private final  CoursesRepository coursesRepository;

    public CoursesServices(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public List<Courses> getCourses(){
        return coursesRepository.findAll();
    }

    Courses getCourseByName(String name){
        return coursesRepository.findByName(name.toLowerCase())
                .orElseThrow(() -> new RuntimeException("Course with name " + name + " not found"));
    }

    @Transactional
    public void deleteCourse(String name) {
        coursesRepository.deleteByName(name);
    }

    @Transactional
    public Courses addCourse(Courses course) {
        if (coursesRepository.findByName(course.getName()).isPresent()) {
            throw new RuntimeException("Course with name " + course.getName() + " already exists");
        }
        coursesRepository.save(course);
        return course;
    }

    @Transactional
    public Courses updateCourse(Courses updatedCourse) {

        Optional<Courses> existingCourse = coursesRepository.findByName(updatedCourse.getName());

        if (existingCourse.isPresent()) {

            Courses courseToUpdate = existingCourse.get();

            if (!updatedCourse.getName().equals(courseToUpdate.getName())) {
                throw new IllegalArgumentException("Course name cannot be changed.");
            }

            courseToUpdate.setTitle(updatedCourse.getTitle());
            courseToUpdate.setDescription(updatedCourse.getDescription());
            courseToUpdate.setCategory(updatedCourse.getCategory());
            coursesRepository.save(courseToUpdate);
            return courseToUpdate;
        }
        return null;
    }


    public List<Courses> getCoursesByCategory(String categoryName){
        return coursesRepository.findByCategory(categoryName);
    }

    public List<Courses> getCoursesByTitle(String categoryTitle){
        return coursesRepository.findByTitle(categoryTitle);
    }

}
