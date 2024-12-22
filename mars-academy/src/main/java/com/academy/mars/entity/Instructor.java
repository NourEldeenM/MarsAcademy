package com.academy.mars.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "instructors")
public class Instructor {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization", nullable = false)
    private InstructorSpecialization specialization;

    @OneToOne
    @MapsId // Maps the ID of this entity to the ID of the related User entity
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "instructor",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CourseInstructors> courses;

}
