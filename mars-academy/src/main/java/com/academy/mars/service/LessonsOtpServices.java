package com.academy.mars.service;

import com.academy.mars.entity.Lessons;
import com.academy.mars.entity.LessonsOtp;
import com.academy.mars.repository.LessonsOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class LessonsOtpServices {

    @Autowired
    private LessonsOtpRepository lessonsOtpRepository;

    @Autowired
    private LessonsServices lessonsServices;

    // POST: Create OTP for a lesson
    public LessonsOtp createOtpForLesson(Long lessonId) {

        Lessons lesson = lessonsServices.getLesson(lessonId);
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson not found");
        }
        if(lessonsOtpRepository.findByLesson(lesson).isPresent()){
            throw new IllegalArgumentException("Lesson already has otp");
        }
        String otp = generateUniqueOtp();
        LessonsOtp lessonsOtp = new LessonsOtp(lesson, otp);
        return lessonsOtpRepository.save(lessonsOtp);
    }

    // GET: Get OTP of a lesson
    public LessonsOtp getOtpForLesson(Long lessonId) {
        Lessons lesson=lessonsServices.getLesson(lessonId);
        Optional<LessonsOtp> lessonsOtp = lessonsOtpRepository.findByLesson(lesson);
        if (lessonsOtp.isPresent()) {
            return lessonsOtp.get();
        } else {
            throw new IllegalArgumentException("There is no OTP found for this lesson");
        }
    }

    // PUT: Update OTP for a lesson
    public LessonsOtp updateOtpForLesson(Long lessonId) {
        Lessons lesson = lessonsServices.getLesson(lessonId);

        Optional<LessonsOtp> optionalLessonsOtp = lessonsOtpRepository.findByLesson(lesson);

        if (optionalLessonsOtp.isPresent()) {
            LessonsOtp lessonsOtp = optionalLessonsOtp.get();

            String otp = generateUniqueOtp();
            lessonsOtp.setOtp(otp);  // Update OTP value

            // Update 'updatedAt' and preserve 'createdAt'
            lessonsOtp.setUpdatedAt(LocalDateTime.now());

            return lessonsOtpRepository.save(lessonsOtp);  // Save updated OTP
        } else {
            return createOtpForLesson(lessonId);
        }
    }

    // DELETE: Delete OTP of a lesson
    public void deleteOtpForLesson(Long lessonId) {
        Lessons lesson = lessonsServices.getLesson(lessonId);

        Optional<LessonsOtp> optionalLessonsOtp = lessonsOtpRepository.findByLesson(lesson);
        if(optionalLessonsOtp.isPresent()){
            lessonsOtpRepository.delete(optionalLessonsOtp.get());
        }else{
         throw new IllegalArgumentException("There is no OTP found for this lesson");
        }
    }


    // Methods
    private String generateUniqueOtp() {
        String generatedOtp;
        boolean otpExists;

        do {
            Random random = new Random();
            int randomOtp = 100000 + random.nextInt(900000);
            generatedOtp=  String.valueOf(randomOtp);
            otpExists = lessonsOtpRepository.existsByOtp(generatedOtp);
        } while (otpExists);
        return generatedOtp;
    }

}
