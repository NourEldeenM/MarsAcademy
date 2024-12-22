package com.academy.mars.repository;

import com.academy.mars.entity.Lessons;
import com.academy.mars.entity.LessonsAttendance;
import com.academy.mars.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonsAttendanceRepository extends JpaRepository<LessonsAttendance, Long> {
    List<LessonsAttendance> findByLesson(Lessons lesson);
    List<LessonsAttendance> findByStudent(Student student);
}
