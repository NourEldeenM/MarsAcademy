package com.academy.mars.lessonAttendance;

import com.academy.mars.lesson.Lessons;
import com.academy.mars.lessonOtp.LessonsOtpRepository;
import com.academy.mars.student.Student;
import com.academy.mars.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','STUDENT')")
    public ResponseEntity<?> addAttendance(@PathVariable String studentId,@RequestParam String otp) {
        try {
            return ResponseEntity.status(200).body(lessonsAttendanceServices.addAttendance(studentId,otp));
        }catch (Exception e){
            return ResponseEntity.status(400).body(json("Error", e.getMessage()));
        }
    }

    // GET API to get the attendance records for a specific lesson
    @GetMapping("/lesson/{lessonId}")
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    public ResponseEntity<List<Student>> getStudentsByLesson(@PathVariable String lessonId) {
        Lessons lesson = new Lessons();
        lesson.setId(lessonId);

        List<Student> students = lessonsAttendanceServices.getStudentsByLesson(lesson);

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET API to get the attendance records for a specific student
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','STUDENT')")
    public ResponseEntity<List<Lessons>> getLessonsByStudent(@PathVariable String studentId) {
        Student student = new Student();
        student.setId(studentId);

        List<Lessons> lessons = lessonsAttendanceServices.getLessonsByStudent(student);

        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }
    private Object json(String key, String message) {
        return Map.of(key, message);
    }
}
