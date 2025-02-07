package com.academy.mars.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getUnreadNotifications(String recipientId) {
        return notificationRepository.findByReceiverIdAndReadFalse(recipientId);
    }

    public List<Notification> getAllNotifications(String recipientId) {
        return notificationRepository.findByReceiverId(recipientId);
    }

    public Notification createAndSendNotification(NotificationType type, String message, String receiverId){
        Notification notification = new Notification(type,message,receiverId);
        return notificationRepository.save(notification);
    }

    public void markAsRead(String notificationId){
        Notification notification = notificationRepository.findById(notificationId)
                        .orElseThrow(() -> new RuntimeException("not found"));
        notification.markAsRead();
        notificationRepository.save(notification);
    }

    public void deleteNotification(String notificationId){
        Notification notification = notificationRepository.findById(notificationId)
                        .orElseThrow(() -> new RuntimeException("not found"));
        notificationRepository.delete(notification);
    }

    public Notification getNotification(String notificationId){
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("not found"));
    }
}
