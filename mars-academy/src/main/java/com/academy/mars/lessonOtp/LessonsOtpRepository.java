package com.academy.mars.lessonOtp;

import com.academy.mars.lesson.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonsOtpRepository extends JpaRepository<LessonsOtp, Long> {
    boolean existsByOtp(String otp);

    Optional<LessonsOtp> findById(Long lessonId);

    Optional<LessonsOtp> findByLesson(Lessons lesson);

    Optional<LessonsOtp> findByOtp(String otp);

}
