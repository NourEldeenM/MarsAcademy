package com.academy.mars.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
public class Notification {
    @Id
    @SequenceGenerator(
            name = "notification_sequence",
            sequenceName = "notification_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notification_sequence"
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private String message;
    private boolean read;
    private LocalDateTime dateTime;
    private Long receiverId;

    public Notification(NotificationType type,String message,Long receiverId){
        this.type = type;
        this.message = message;
        this.receiverId = receiverId;
        this.read = false;
        this.dateTime = LocalDateTime.now();
    }

    public void markAsRead(){
        this.read = true;
    }
}
