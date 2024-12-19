//package com.academy.mars.entity;
//
//import com.academy.mars.CourseManagement.Courses.Courses;
//import com.academy.mars.NotificationsManagement.Notification;
//import jakarta.persistence.*;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//
//@Getter
//@Setter
//@EqualsAndHashCode(callSuper = true)
//@NoArgsConstructor
//@Entity
//@Table(name = "students")
//public class Student extends User {
//
//    @ManyToMany
//    @JoinTable(
//            name = "student_courses",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id")
//    )
//    private List<Courses> enrolledCourses;
//
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Notification> notifications;
//}
