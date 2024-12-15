package com.academy.mars.NotificationsManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getUnreadNotifications(Long recipientId) {
        return notificationRepository.findByReceiverIdAndReadFalse(recipientId);
    }

    public List<Notification> getAllNotifications(Long recipientId) {
        return notificationRepository.findByReceiverId(recipientId);
    }

    public Notification createAndSendNotification(NotificationType type,String message,Long receiverId){
        Notification notification = new Notification(type,message,receiverId);
        return notificationRepository.save(notification);
    }

    public void markAsRead(Notification notification){
        notification.markAsRead();
        notificationRepository.save(notification);
    }
}
