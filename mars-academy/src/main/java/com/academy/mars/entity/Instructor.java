package com.academy.mars.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "instructors")
public class Instructor {

    @Id
    private Long id; // Use this to map to User's ID

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization", nullable = false)
    private InstructorSpecialization specialization;

    @OneToOne
    @MapsId // Maps the ID of this entity to the ID of the related User entity
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

}
