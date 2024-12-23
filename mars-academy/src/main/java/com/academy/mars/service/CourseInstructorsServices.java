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
        Courses course = courseExist(courseId).get();
        Instructor instructor = instructorExist(instructorId).get();

        CourseInstructors courseInstructors = new CourseInstructors(course, instructor);
        return courseInstructorsRepository.save(courseInstructors);
    }

    public List<CourseInstructors> getInstructorsByCourse(Long courseId) {
        Courses course = courseExist(courseId).get();
        return courseInstructorsRepository.findByCourse(course);
    }

    public List<CourseInstructors> getCoursesByInstructor(Long instructorId) {
        Instructor instructor = instructorExist(instructorId).get();
        return courseInstructorsRepository.findByInstructor(instructor);
    }

    @Transactional
    public void removeInstructorFromCourse(Long courseId, Long instructorId) {
        Courses course = courseExist(courseId).get();
        Instructor instructor = instructorExist(instructorId).get();
        courseInstructorsRepository.deleteByCourseAndInstructor(course, instructor);
    }

    @Transactional
    public void removeAllInstructorsFromCourse(Long courseId) {
        Courses course = courseExist(courseId).get();
        courseInstructorsRepository.deleteByCourse(course);
    }

    public boolean isACourseInstructor(Long courseId, Long instructorId) {
        Courses course = courseExist(courseId).get();
        Instructor instructor = instructorExist(instructorId).get();
        return courseInstructorsRepository.findByCourseAndInstructor(course, instructor).isPresent();
    }

    private Optional<Courses> courseExist(Long id){
        Optional<Courses> courseOptional = coursesRepository.findById(id);
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + id + " not found");
        }
        return courseOptional;
    }

    private Optional<Instructor> instructorExist(Long id){
        Optional<Instructor> userOptional = instructorRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Instructor with ID " + id + " not found");
        }
        return userOptional;
    }

}
