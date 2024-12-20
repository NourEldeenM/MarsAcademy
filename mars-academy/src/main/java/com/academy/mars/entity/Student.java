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

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinTable(
            name = "student_notifications",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"),
            indexes = @Index(columnList = "student_id")

    )
    private List<Notification> notifications;
}
