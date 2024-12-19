package com.academy.mars.CourseManagement.CourseEnrollments;

import com.academy.mars.CourseManagement.Courses.Courses;
import com.academy.mars.CourseManagement.Courses.CoursesRepository;
import com.academy.mars.entity.User;
import com.academy.mars.repository.UserRepository;
import com.academy.mars.entity.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseEnrollmentsServices {
    private final CourseEnrollmentsRepository courseEnrollmentsRepository;
    private final UserRepository userRepository;
    private final CoursesRepository coursesRepository;

    @Autowired
    public CourseEnrollmentsServices(CourseEnrollmentsRepository courseEnrollmentsRepository, UserRepository userRepository, CoursesRepository coursesRepository) {
        this.courseEnrollmentsRepository = courseEnrollmentsRepository;
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
    }

    @Transactional
    public void enrollStudentInCourse(Long courseId, Long studentId){
        Optional<User> userOptional = userRepository.findById(studentId);
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Student with ID " + studentId + " not found");
        }
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }

        Courses course = courseOptional.get();
        User user = userOptional.get();

        // Edge case
        if(courseEnrollmentsRepository.isStudentEnrolledInCourse(user, course)){
            throw new RuntimeException("Student with ID " + studentId + " is already enrolled in course with ID " + courseId);
        }

        CourseEnrollments enrollment = new CourseEnrollments(user, course);
        courseEnrollmentsRepository.save(enrollment);
    }

    public void unEnrollStudentInCourse(Long courseId, Long studentId){
        Optional<User> userOptional = userRepository.findById(studentId);
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Student with ID " + studentId + " not found");
        }
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }

        Courses course = courseOptional.get();
        User user = userOptional.get();
        if(!courseEnrollmentsRepository.isStudentEnrolledInCourse(user, course)){
            throw new RuntimeException("Student with ID " + studentId + " is not enrolled in the course with ID " + courseId);
        }
        CourseEnrollments courseEnrollment = courseEnrollmentsRepository.findByUserAndCourse(user, course);
        courseEnrollmentsRepository.delete(courseEnrollment);
    }

    public List<User> getAllStudentsInCourse(Long courseId){
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }

        Courses course = courseOptional.get();
        List<User> students = courseEnrollmentsRepository.findAllStudentsInACourse(course);
       //make program continue untill authentication
        for (User student : students) {
            if (student.getRole() == null) {
                student.setRole(UserRole.ROLE_STUDENT); // Assign a default role
            }
        }
        return students;
    }

    //return all courses of student
    //need to get api and call it
     public List<Courses> findAllCoursesStudentEnrollIn(Long courseId, Long studentId){
         Optional<User> userOptional = userRepository.findById(studentId);
         if(userOptional.isEmpty()){
             throw new RuntimeException("Student with ID " + studentId + " not found");
         }
         User user = userOptional.get();

         List<Courses> courses=courseEnrollmentsRepository.findAllCoursesOfAStudent(user);
         return courses;
     }
}
