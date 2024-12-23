package com.academy.mars.repository;

import com.academy.mars.entity.Lessons;
import com.academy.mars.entity.LessonsOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LessonsOtpRepository extends JpaRepository<LessonsOtp, Long> {
    boolean existsByOtp(String otp);

    Optional<LessonsOtp> findById(Long lessonId);

    Optional<LessonsOtp> findByLesson(Lessons lesson);

    Optional<LessonsOtp> findByOtp(String otp);

}
