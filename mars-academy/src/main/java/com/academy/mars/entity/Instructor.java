//package com.academy.mars.entity;
//
//import com.academy.mars.CourseManagement.Courses.Courses;
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
//@Table(name = "instructors")
//public class Instructor extends User {
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "specialization", nullable = false)
//    private InstructorSpecialization specialization;
//
//    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Courses> coursesTaught;
//}
