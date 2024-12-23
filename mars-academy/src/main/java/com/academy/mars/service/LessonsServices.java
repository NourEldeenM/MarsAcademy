package com.academy.mars.service;

import com.academy.mars.entity.Courses;
import com.academy.mars.entity.Lessons;
import com.academy.mars.repository.LessonsRepository;
import com.academy.mars.service.CoursesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonsServices {

    private final LessonsRepository lessonsRepository;
    private final CoursesServices coursesServices;

    @Autowired
    public LessonsServices(LessonsRepository lessonsRepository , CoursesServices coursesServices) {
        this.lessonsRepository = lessonsRepository;
        this.coursesServices = coursesServices;
    }

    public Lessons addLesson(Long courseId, Lessons lesson) {
        if (!coursesServices.courseExist(courseId)) {
            throw new RuntimeException("There is no Course id with id "+courseId);
        }
        Courses course = coursesServices.getCourseById(courseId);
        lesson.setCourse(course);
        return lessonsRepository.save(lesson);
    }

    public List<Lessons> getAllLessons(Long courseId) {
        return lessonsRepository.findByCourseId(courseId);
    }

    public Lessons getLesson(Long lessonId) {
        Optional<Lessons> lesson = lessonsRepository.findById( lessonId);
        if(lesson.isEmpty()){
            throw new RuntimeException("There is no Lesson with id "+lessonId);
        }
        return lesson.get();
    }

    public Lessons updateLesson(Long courseId, Long lessonId, Lessons updatedLesson) {
        Optional<Lessons> existingLesson = lessonsRepository.findById( lessonId);

        if(existingLesson.isEmpty()){
            throw new RuntimeException("There is no Lesson with id "+lessonId);
        }
        if(courseId!=existingLesson.get().getCourse().getId()){
            throw new RuntimeException("There is no Lesson with id "+lessonId + " in course "+ courseId );
        }
        Lessons lesson = existingLesson.get();
        lesson.setTitle(updatedLesson.getTitle());
        lesson.setDescription(updatedLesson.getDescription());
        lesson.setContent(updatedLesson.getContent());
        lesson.setDuration(updatedLesson.getDuration());
        return lessonsRepository.save(lesson);
    }

    public void deleteLesson( Long courseId,Long lessonId) {
        Optional<Lessons> existingLesson = lessonsRepository.findById( lessonId);

        if(existingLesson.isEmpty()){
            throw new RuntimeException("There is no Lesson with id "+lessonId);
        }
        if(courseId!=existingLesson.get().getCourse().getId()){
            throw new RuntimeException("There is no Lesson with id "+lessonId + " in course "+ courseId );
        }
        lessonsRepository.delete(existingLesson.get());
    }

    public void deleteAllLessonsOfCourse( Long courseId) {
        List<Lessons> existingLessons = lessonsRepository.findByCourseId( courseId);

        if(existingLessons.isEmpty()){
            throw new RuntimeException("There is no Lessons in course with id "+courseId);
        }
        for(Lessons l:existingLessons){
            deleteLesson(courseId,l.getId());
        }

    }
}
