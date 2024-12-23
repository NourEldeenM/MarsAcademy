package com.academy.mars.controller;

import com.academy.mars.service.NotificationService;
import com.academy.mars.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/{userId}/all")
    public ResponseEntity<?> getAllNotifications(@PathVariable Long userId){
        List<Notification> notifications = notificationService.getAllNotifications(userId);
        if (notifications != null){
            return ResponseEntity.ok(notifications);
        }
        else
            return ResponseEntity.badRequest().body("ID undefined");
    }

    @GetMapping("/{userId}/unread")
    public ResponseEntity<?> getUnreadNotifications(@PathVariable Long userId){
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        if (notifications != null){
            return ResponseEntity.ok(notifications);
        }
        else
            return ResponseEntity.badRequest().body("ID undefined");
    }

    @GetMapping("/{userId}/{notificationId}")
    public ResponseEntity<?> getNotification(@PathVariable Long notificationId){
        Notification notification = notificationService.getNotification(notificationId);
        if (notification != null){
            return ResponseEntity.ok(notification);
        }
        else
            return ResponseEntity.badRequest().body("ID undefined");
    }

    @PutMapping("/{userId}/{notificationId}/markAsRead")
    public ResponseEntity<String> markAsRead(@PathVariable Long notificationId){
        try {
            notificationService.markAsRead(notificationId);
            return ResponseEntity.ok("marked read");
        }
        catch (RuntimeException exception){
            return ResponseEntity.badRequest().body("notification not found");
        }
    }

    @DeleteMapping("/{userId}/{notificationId}/deleteNotification")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId){
        try {
            notificationService.deleteNotification(notificationId);
            return ResponseEntity.ok("deleted successfully");
        }
        catch (RuntimeException exception){
            return ResponseEntity.badRequest().body("notification not found");
        }
    }

}
