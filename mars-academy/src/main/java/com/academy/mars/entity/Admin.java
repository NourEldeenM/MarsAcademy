package com.academy.mars.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    private Long id; // Use this to map to User's ID

    @OneToOne
    @MapsId // Maps the ID of this entity to the ID of the related User entity
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

}
