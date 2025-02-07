package com.academy.mars.notification;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private String message;
    private boolean read;
    private LocalDateTime dateTime;
    private String receiverId;

    public Notification(NotificationType type,String message,String receiverId){
        this.type = type;
        this.message = message;
        this.receiverId = receiverId;
        this.read = false;
        this.dateTime = LocalDateTime.now();
    }

    public Notification() {}

    public void markAsRead(){
        this.read = true;
    }
}
