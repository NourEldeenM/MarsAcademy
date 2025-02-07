package com.academy.mars.lessonAttendance;

import com.academy.mars.lesson.Lessons;
import com.academy.mars.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonsAttendanceRepository extends JpaRepository<LessonsAttendance, String> {
    List<LessonsAttendance> findByLesson(Lessons lesson);
    List<LessonsAttendance> findByStudent(Student student);
}
