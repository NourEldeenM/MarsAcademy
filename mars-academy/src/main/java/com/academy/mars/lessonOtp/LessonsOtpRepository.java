package com.academy.mars.lessonOtp;

import com.academy.mars.lesson.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonsOtpRepository extends JpaRepository<LessonsOtp, String> {
    boolean existsByOtp(String otp);

    Optional<LessonsOtp> findById(String lessonId);

    Optional<LessonsOtp> findByLesson(Lessons lesson);

    Optional<LessonsOtp> findByOtp(String otp);

}
