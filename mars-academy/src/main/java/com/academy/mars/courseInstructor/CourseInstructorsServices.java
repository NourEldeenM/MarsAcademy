package com.academy.mars.courseInstructor;

import com.academy.mars.course.Courses;
import com.academy.mars.course.CoursesRepository;
import com.academy.mars.instructor.Instructor;
import com.academy.mars.instructor.InstructorRepository;
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
    public CourseInstructors addInstructorToCourse(String courseId, String instructorId) {
        Courses course = courseExist(courseId).get();
        Instructor instructor = instructorExist(instructorId).get();

        CourseInstructors courseInstructors = new CourseInstructors(course, instructor);
        return courseInstructorsRepository.save(courseInstructors);
    }

    public List<CourseInstructors> getInstructorsByCourse(String courseId) {
        Courses course = courseExist(courseId).get();
        return courseInstructorsRepository.findByCourse(course);
    }

    public List<CourseInstructors> getCoursesByInstructor(String instructorId) {
        Instructor instructor = instructorExist(instructorId).get();
        return courseInstructorsRepository.findByInstructor(instructor);
    }

    @Transactional
    public void removeInstructorFromCourse(String courseId, String instructorId) {
        Courses course = courseExist(courseId).get();
        Instructor instructor = instructorExist(instructorId).get();
        courseInstructorsRepository.deleteByCourseAndInstructor(course, instructor);
    }

    @Transactional
    public void removeAllInstructorsFromCourse(String courseId) {
        Courses course = courseExist(courseId).get();
        courseInstructorsRepository.deleteByCourse(course);
    }

    public boolean isACourseInstructor(String courseId, String instructorId) {
        Courses course = courseExist(courseId).get();
        Instructor instructor = instructorExist(instructorId).get();
        return courseInstructorsRepository.findByCourseAndInstructor(course, instructor).isPresent();
    }

    private Optional<Courses> courseExist(String id){
        Optional<Courses> courseOptional = coursesRepository.findById(id);
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + id + " not found");
        }
        return courseOptional;
    }

    private Optional<Instructor> instructorExist(String id){
        Optional<Instructor> userOptional = instructorRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Instructor with ID " + id + " not found");
        }
        return userOptional;
    }

}
