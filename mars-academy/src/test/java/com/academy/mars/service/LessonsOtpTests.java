package com.academy.mars.service;

import com.academy.mars.entity.Lessons;
import com.academy.mars.entity.LessonsOtp;
import com.academy.mars.repository.LessonsOtpRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonsOtpTests {

    @Mock
    private LessonsOtpRepository lessonsOtpRepository;

    @Mock
    private LessonsServices lessonsServices;

    @InjectMocks
    private LessonsOtpServices lessonsOtpServices;

    @Test
    void testCreateOtpForLesson_Success() {
        Long lessonId = 1L;
        Lessons lesson = new Lessons();
        lesson.setId(lessonId);

        when(lessonsServices.getLesson(lessonId)).thenReturn(lesson);
        when(lessonsOtpRepository.findByLesson(lesson)).thenReturn(Optional.empty());
        when(lessonsOtpRepository.save(any(LessonsOtp.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LessonsOtp otp = lessonsOtpServices.createOtpForLesson(lessonId);

        assertNotNull(otp);
        assertEquals(lesson, otp.getLesson());
        assertNotNull(otp.getOtp());
        verify(lessonsOtpRepository, times(1)).save(otp);
    }

    @Test
    void testCreateOtpForLesson_AlreadyHasOtp() {
        Long lessonId = 1L;
        Lessons lesson = new Lessons();
        lesson.setId(lessonId);

        when(lessonsServices.getLesson(lessonId)).thenReturn(lesson);
        when(lessonsOtpRepository.findByLesson(lesson)).thenReturn(Optional.of(new LessonsOtp()));

        assertThrows(IllegalArgumentException.class, () -> lessonsOtpServices.createOtpForLesson(lessonId));
    }

    @Test
    void testGetOtpForLesson_Success() {
        Long lessonId = 1L;
        Lessons lesson = new Lessons();
        lesson.setId(lessonId);
        LessonsOtp otp = new LessonsOtp(lesson, "123456");

        when(lessonsServices.getLesson(lessonId)).thenReturn(lesson);
        when(lessonsOtpRepository.findByLesson(lesson)).thenReturn(Optional.of(otp));

        LessonsOtp foundOtp = lessonsOtpServices.getOtpForLesson(lessonId);

        assertNotNull(foundOtp);
        assertEquals("123456", foundOtp.getOtp());
    }

    @Test
    void testGetOtpForLesson_NotFound() {
        Long lessonId = 1L;
        Lessons lesson = new Lessons();
        lesson.setId(lessonId);

        when(lessonsServices.getLesson(lessonId)).thenReturn(lesson);
        when(lessonsOtpRepository.findByLesson(lesson)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> lessonsOtpServices.getOtpForLesson(lessonId));
    }

    @Test
    void testUpdateOtpForLesson_CreateNewOtp() {
        Long lessonId = 1L;
        Lessons lesson = new Lessons();
        lesson.setId(lessonId);

        when(lessonsServices.getLesson(lessonId)).thenReturn(lesson);
        when(lessonsOtpRepository.findByLesson(lesson)).thenReturn(Optional.empty());
        when(lessonsOtpRepository.save(any(LessonsOtp.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LessonsOtp updatedOtp = lessonsOtpServices.updateOtpForLesson(lessonId);

        assertNotNull(updatedOtp);
        assertEquals(lesson, updatedOtp.getLesson());
        assertNotNull(updatedOtp.getOtp());
    }

    @Test
    void testDeleteOtpForLesson_Success() {
        Long lessonId = 1L;
        Lessons lesson = new Lessons();
        lesson.setId(lessonId);
        LessonsOtp otp = new LessonsOtp(lesson, "123456");

        when(lessonsServices.getLesson(lessonId)).thenReturn(lesson);
        when(lessonsOtpRepository.findByLesson(lesson)).thenReturn(Optional.of(otp));

        lessonsOtpServices.deleteOtpForLesson(lessonId);

        verify(lessonsOtpRepository, times(1)).delete(otp);
    }

    @Test
    void testDeleteOtpForLesson_NotFound() {
        Long lessonId = 1L;
        Lessons lesson = new Lessons();
        lesson.setId(lessonId);

        when(lessonsServices.getLesson(lessonId)).thenReturn(lesson);
        when(lessonsOtpRepository.findByLesson(lesson)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> lessonsOtpServices.deleteOtpForLesson(lessonId));
    }
}
