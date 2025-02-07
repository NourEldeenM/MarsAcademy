package com.academy.mars.courseEnrollment;

import com.academy.mars.course.Courses;
import com.academy.mars.course.CoursesRepository;
import com.academy.mars.student.Student;
import com.academy.mars.student.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseEnrollmentsServices {
    private final CourseEnrollmentsRepository courseEnrollmentsRepository;
    private final CoursesRepository coursesRepository;

    private final StudentRepository studentRepository;

    @Autowired
    public CourseEnrollmentsServices(CourseEnrollmentsRepository courseEnrollmentsRepository, CoursesRepository coursesRepository, StudentRepository studentRepository) {
        this.courseEnrollmentsRepository = courseEnrollmentsRepository;
        this.coursesRepository = coursesRepository;
        this.studentRepository = studentRepository;
    }



    @Transactional
    public void enrollStudentInCourse(String courseId, String studentId){
        Courses course = courseExist(courseId).get();
        Student student = studentExist(studentId).get();

        if(courseEnrollmentsRepository.isStudentEnrolledInCourse(student, course)){
            throw new RuntimeException("Student with ID " + studentId + " is already enrolled in course with ID " + courseId);
        }

        CourseEnrollments enrollment = new CourseEnrollments(student, course);
        courseEnrollmentsRepository.save(enrollment);
    }

    public void unEnrollStudentInCourse(String courseId, String studentId){
        Courses course = courseExist(courseId).get();
        Student student = studentExist(studentId).get();

        if(!courseEnrollmentsRepository.isStudentEnrolledInCourse(student, course)){
            throw new RuntimeException("Student with ID " + studentId + " is not enrolled in the course with ID " + courseId);
        }
        CourseEnrollments courseEnrollment = courseEnrollmentsRepository.findByStudentAndCourse(student, course);
        courseEnrollmentsRepository.delete(courseEnrollment);
    }

    public List<Student> getAllStudentsInCourse(String courseId){
        Courses course = courseExist(courseId).get();

        List<Student> students = courseEnrollmentsRepository.findByCourse(course);
        return students;
    }

     public List<Courses> findAllCoursesStudentEnrollIn( String studentId){
         Student student = studentExist(studentId).get();
         List<Courses> courses=courseEnrollmentsRepository.findByStudent(student);
         return courses;
     }

    private Optional<Courses> courseExist(String id){
        Optional<Courses> courseOptional = coursesRepository.findById(id);
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course with ID " + id + " not found");
        }
        return courseOptional;
    }

    private Optional<Student> studentExist(String id){
        Optional<Student> userOptional = studentRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Student with ID " + id + " not found");
        }
        return userOptional;
    }
}
