package com.academy.mars.CourseManagement.CourseEnrollments;

import com.academy.mars.CourseManagement.Courses.Courses;
import com.academy.mars.CourseManagement.Courses.CoursesRepository;
import com.academy.mars.UserManagement.User;
import com.academy.mars.UserManagement.UserRepository;
import com.academy.mars.UserManagement.UserRole;
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
            throw new RuntimeException("Student not found");
        }
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course not found");
        }

        Courses course=courseOptional.get();
        User user=userOptional.get();

//        edge case
        if(courseEnrollmentsRepository.isStudentEnrolledInCourse(user,course)){
            throw new RuntimeException("Student already enrolled in course.");
        }



        CourseEnrollments enrollment = new CourseEnrollments(user, course);
        courseEnrollmentsRepository.save(enrollment);
    }

//    public void studentUnEnrollInCourse(Long course_id,Long studentId){
//
//    }
    public List<User> getAllStudentsInCourse(Long courseId){
        Optional<Courses> courseOptional = coursesRepository.findById(courseId);
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course not found");
        }

        Courses course=courseOptional.get();
        List<User> students= courseEnrollmentsRepository.findAllStudentsInACourse(course);
        for (User student : students) {
            if (student.getUserRole() == null) {
                student.setUserRole(UserRole.User); // Assign a default role
            }
        }
        return students;
    }
//
//    public List<Courses> findAllCoursesStudentEnrollIn(Long course_id,Long studentId){
//
//    }

}
