package com.academy.mars.repository;

import com.academy.mars.entity.Lessons;
import com.academy.mars.entity.LessonsOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LessonsOtpRepository extends JpaRepository<LessonsOtp, Lessons> {
    boolean existsByOtp(String otp);

    Optional<LessonsOtp> findById(Long lessonId);
    Optional<LessonsOtp> findByLesson(Lessons lesson);

//    default LessonsOtp update(LessonsOtp lessonsOtp) {
//        Optional<LessonsOtp> existingOtp = findById(lessonsOtp.getLesson().getId());
//        LessonsOtp updatedOtp = existingOtp.get();
//        updatedOtp.setOtp(lessonsOtp.getOtp());
//        updatedOtp.setUpdatedAt(LocalDateTime.now());
//        return save(updatedOtp);
//    }
}
