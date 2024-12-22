package com.academy.mars.controller;

import com.academy.mars.entity.Lessons;
import com.academy.mars.entity.Student;
import com.academy.mars.repository.LessonsOtpRepository;
import com.academy.mars.repository.StudentRepository;
import com.academy.mars.service.LessonsAttendanceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
public class LessonsAttendanceController {

    private final LessonsAttendanceServices lessonsAttendanceServices;
    private final LessonsOtpRepository lessonsOtpRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public LessonsAttendanceController(LessonsAttendanceServices lessonsAttendanceServices, LessonsOtpRepository lessonsOtpRepository, StudentRepository studentRepository) {
        this.lessonsAttendanceServices = lessonsAttendanceServices;
        this.lessonsOtpRepository = lessonsOtpRepository;
        this.studentRepository = studentRepository;
    }

    // POST API to add attendance for a student in a lesson
    @PostMapping("/student/{studentId}")
    public ResponseEntity<?> addAttendance(@PathVariable Long studentId,@RequestParam String otp) {
        try {
            return ResponseEntity.status(200).body(lessonsAttendanceServices.addAttendance(studentId,otp));
        }catch (Exception e){
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    // GET API to get the attendance records for a specific lesson
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<Student>> getStudentsByLesson(@PathVariable Long lessonId) {
        Lessons lesson = new Lessons();
        lesson.setId(lessonId);

        List<Student> students = lessonsAttendanceServices.getStudentsByLesson(lesson);

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET API to get the attendance records for a specific student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Lessons>> getLessonsByStudent(@PathVariable Long studentId) {
        Student student = new Student();
        student.setId(studentId);

        List<Lessons> lessons = lessonsAttendanceServices.getLessonsByStudent(student);

        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }
    private Object json(String key, String message) {
        return Map.of(key, message);
    }
}
