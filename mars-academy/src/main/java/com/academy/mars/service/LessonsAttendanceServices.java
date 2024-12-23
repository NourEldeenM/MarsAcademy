package com.academy.mars.service;

import com.academy.mars.entity.Lessons;
import com.academy.mars.entity.LessonsAttendance;
import com.academy.mars.entity.LessonsOtp;
import com.academy.mars.entity.Student;
import com.academy.mars.repository.LessonsAttendanceRepository;
import com.academy.mars.repository.LessonsOtpRepository;
import com.academy.mars.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonsAttendanceServices {

    private final LessonsAttendanceRepository lessonsAttendanceRepository;
    private final StudentRepository studentRepository;
    private final LessonsOtpRepository lessonsOtpRepository;


    @Autowired
    public LessonsAttendanceServices(LessonsAttendanceRepository lessonsAttendanceRepository, StudentRepository studentRepository, LessonsOtpRepository lessonsOtpRepository) {
        this.lessonsAttendanceRepository = lessonsAttendanceRepository;
        this.studentRepository = studentRepository;
        this.lessonsOtpRepository = lessonsOtpRepository;
    }

    // Get all attendance records for a specific lesson
    public List<LessonsAttendance> getAttendancesByLesson(Lessons lesson) {
        return lessonsAttendanceRepository.findByLesson(lesson);
    }

    // Get all attendance records for a specific student
    public List<LessonsAttendance> getAttendancesByStudent(Student student) {
        return lessonsAttendanceRepository.findByStudent(student);
    }

    // Get students attending a specific lesson
    public List<Student> getStudentsByLesson(Lessons lesson) {
        List<LessonsAttendance> records= getAttendancesByLesson(lesson);
        List<Student> students=new ArrayList<>();;

        for(LessonsAttendance l:records){
            students.add(l.getStudent());
        }
        return students;
    }

    // Get lessons attended by a specific student
    public List<Lessons> getLessonsByStudent(Student student) {
        List<LessonsAttendance> records=lessonsAttendanceRepository.findByStudent(student);

        List<Lessons> lessons=new ArrayList<>();;

        for(LessonsAttendance l:records){
            lessons.add(l.getLesson());
        }
        return lessons;
    }

    // Add attendance record for a student in a lesson
    public LessonsAttendance addAttendance(Long studentId, String otp) {
        Optional<Student> student=studentRepository.findById(studentId);
        if(student.isEmpty()){
            throw new RuntimeException( "Student not found");
        }

        Optional<LessonsOtp> optionalLessonsOtp = lessonsOtpRepository.findByOtp(otp);
        if(optionalLessonsOtp.isEmpty()){
            throw  new RuntimeException("Otp is wrong");
        }
        LessonsAttendance lessonsAttendance=new LessonsAttendance(optionalLessonsOtp.get().getLesson(), student.get());
        return lessonsAttendanceRepository.save(lessonsAttendance);
    }
}
