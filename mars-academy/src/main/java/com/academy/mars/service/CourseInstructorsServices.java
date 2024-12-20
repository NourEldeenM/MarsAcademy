package com.academy.mars.service;

import com.academy.mars.entity.CourseInstructors;
import com.academy.mars.entity.Courses;
import com.academy.mars.entity.User;
import com.academy.mars.repository.CourseInstructorsRepository;
import com.academy.mars.repository.CoursesRepository;
import com.academy.mars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CourseInstructorsServices {

    private final CourseInstructorsRepository courseInstructorsRepository;

    private final UserRepository userRepository;
    private final CoursesRepository coursesRepository;
    @Autowired
    public CourseInstructorsServices(CourseInstructorsRepository courseInstructorsRepository, UserRepository userRepository, CoursesRepository coursesRepository) {
        this.courseInstructorsRepository = courseInstructorsRepository;
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
    }



    public CourseInstructors addInstructorToCourse(Long courseId, Long instructorId) {
        Optional<User> userOptional = userRepository.findById(instructorId);
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Instructor with ID " + instructorId + " not found");
        }
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }

        Courses course = courseOptional.get();
        User instructor = userOptional.get();

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
        Optional<User> userOptional = userRepository.findById(instructorId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Instructor with ID " + instructorId + " not found");
        }
        User instructor = userOptional.get();
        return courseInstructorsRepository.findByInstructor(instructor);
    }

    public void removeInstructorFromCourse(Long courseId, Long instructorId) {
        Optional<User> userOptional = userRepository.findById(instructorId);
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Instructor with ID " + instructorId + " not found");
        }
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }

        Courses course = courseOptional.get();
        User instructor = userOptional.get();

        courseInstructorsRepository.deleteByCourseAndInstructor(course, instructor);
    }

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
        Optional<User> userOptional = userRepository.findById(instructorId);
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Instructor with ID " + instructorId + " not found");
        }
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }

        Courses course = courseOptional.get();
        User instructor = userOptional.get();

        return courseInstructorsRepository.findByCourseAndInstructor(course, instructor).isPresent();
    }
}
