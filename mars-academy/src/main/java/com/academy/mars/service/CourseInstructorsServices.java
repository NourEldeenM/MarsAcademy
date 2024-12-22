package com.academy.mars.service;

import com.academy.mars.entity.CourseInstructors;
import com.academy.mars.entity.Courses;
import com.academy.mars.entity.Instructor;
import com.academy.mars.repository.CourseInstructorsRepository;
import com.academy.mars.repository.CoursesRepository;
import com.academy.mars.repository.InstructorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CourseInstructorsServices {

    private final CourseInstructorsRepository courseInstructorsRepository;

    private final InstructorRepository instructorRepository;
    private final CoursesRepository coursesRepository;

    @Autowired
    public CourseInstructorsServices(CourseInstructorsRepository courseInstructorsRepository, InstructorRepository instructorRepository, CoursesRepository coursesRepository) {
        this.courseInstructorsRepository = courseInstructorsRepository;
        this.instructorRepository = instructorRepository;
        this.coursesRepository = coursesRepository;
    }

    @Transactional
    public CourseInstructors addInstructorToCourse(Long courseId, Long instructorId) {
        Optional<Instructor> userOptional = instructorRepository.findById(instructorId);
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Instructor with ID " + instructorId + " not found");
        }
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }

        Courses course = courseOptional.get();
        Instructor instructor = userOptional.get();

        CourseInstructors courseInstructors = new CourseInstructors(course, instructor);
        return courseInstructorsRepository.save(courseInstructors);
    }

    // Get all instructors for a specific course
    public List<CourseInstructors> getInstructorsByCourse(Long courseId) {
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }

        Courses course = courseOptional.get();
        return courseInstructorsRepository.findByCourse(course);
    }

    public List<CourseInstructors> getCoursesByInstructor(Long instructorId) {
        Optional<Instructor> userOptional = instructorRepository.findById(instructorId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Instructor with ID " + instructorId + " not found");
        }
        Instructor instructor = userOptional.get();
        return courseInstructorsRepository.findByInstructor(instructor);
    }

    @Transactional
    public void removeInstructorFromCourse(Long courseId, Long instructorId) {
        Optional<Instructor> userOptional = instructorRepository.findById(instructorId);
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Instructor with ID " + instructorId + " not found");
        }
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }

        Courses course = courseOptional.get();
        Instructor instructor = userOptional.get();

        courseInstructorsRepository.deleteByCourseAndInstructor(course, instructor);
    }

    @Transactional
    public void removeAllInstructorsFromCourse(Long courseId) {
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }

        Courses course = courseOptional.get();
        courseInstructorsRepository.deleteByCourse(course);
    }

    // Find a specific course-instructor relationship
    public boolean isACourseInstructor(Long courseId, Long instructorId) {
        Optional<Instructor> userOptional = instructorRepository.findById(instructorId);
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Instructor with ID " + instructorId + " not found");
        }
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }

        Courses course = courseOptional.get();
        Instructor instructor = userOptional.get();

        return courseInstructorsRepository.findByCourseAndInstructor(course, instructor).isPresent();
    }
}
