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
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

}
