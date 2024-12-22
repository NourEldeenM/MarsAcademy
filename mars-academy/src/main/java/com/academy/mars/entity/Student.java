package com.academy.mars.entity;

import com.academy.mars.NotificationsManagement.Notification;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


//@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {

    @Id
    private Long id; // Use this to map to User's ID

    @OneToOne
    @MapsId // Maps the ID of this entity to the ID of the related User entity
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany
    @JoinTable(
            name = "student_notifications",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"),
            indexes = @Index(columnList = "student_id")

    )
    private List<Notification> notifications;
}
