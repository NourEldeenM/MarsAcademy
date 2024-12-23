package com.academy.mars.service;

import com.academy.mars.entity.Courses;
import com.academy.mars.entity.Grade;
import com.academy.mars.entity.ReportDto;
import com.academy.mars.entity.Student;
import com.academy.mars.repository.CoursesRepository;
import com.academy.mars.repository.GradeRepository;
import com.academy.mars.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private CoursesServices coursesServices;

    @Autowired
    public ReportService(StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<ReportDto> getReportByCourse(Long courseId) {
        Optional<Courses> course = coursesServices.getCourseById(courseId);        List<Student> students = studentRepository.findStudentsByCourses_Id(courseId);
        List<ReportDto> reports = new ArrayList<>();

        for (Student student : students) {
            List<Grade> grades = gradeRepository.findByStudentAndCourse(student, course);

            for (Grade grade : grades) {
                ReportDto report = new ReportDto(
                        student.getId(),
                        student.getUser().getUsername(),
                        grade.getCourse().getName(),
                        grade.getScore(),
                        grade.getFeedback()
                );
                reports.add(report);
            }
        }
        return reports;
    }
}
