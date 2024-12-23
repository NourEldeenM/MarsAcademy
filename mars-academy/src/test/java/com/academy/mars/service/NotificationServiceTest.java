package com.academy.mars.service;

import com.academy.mars.entity.Notification;
import com.academy.mars.entity.NotificationType;
import com.academy.mars.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Mock
    NotificationRepository notificationRepository;

    @InjectMocks
    NotificationService notificationService;

    @Test
    void testUnreadNotification(){
        Long recipientId = 1L;
        List<Notification> mockNotifications = List.of(
                new Notification(NotificationType.COURSE_UPDATES, "Unread Message", recipientId)
        );

        when(notificationRepository.findByReceiverIdAndReadFalse(recipientId))
                .thenReturn(mockNotifications);

        List<Notification> result = notificationService.getUnreadNotifications(recipientId);

        assertEquals(1, result.size());
        assertEquals("Unread Message", result.getFirst().getMessage());
        verify(notificationRepository, times(1))
                .findByReceiverIdAndReadFalse(recipientId);
    }

    @Test
    void testGetAllNotifications() {
        Long recipientId = 2L;
        List<Notification> mockNotifications = List.of(
                new Notification(NotificationType.ENROLLMENT_CONFIRMATION, "Message 1", recipientId),
                new Notification(NotificationType.GRADED_ASSIGNMENT, "Message 2", recipientId)
        );

        when(notificationRepository.findByReceiverId(recipientId))
                .thenReturn(mockNotifications);

        List<Notification> result = notificationService.getAllNotifications(recipientId);

        assertEquals(2, result.size());
        verify(notificationRepository, times(1))
                .findByReceiverId(recipientId);
    }

    @Test
    void testCreateAndSendNotification() {
        Long receiverId = 3L;
        NotificationType type = NotificationType.NEW_COURSE_ENROLLMENT;
        String message = "New Notification";
        Notification notification = new Notification(type, message, receiverId);

        when(notificationRepository.save(any(Notification.class)))
                .thenReturn(notification);

        Notification result = notificationService.createAndSendNotification(type, message, receiverId);

        assertEquals(type, result.getType());
        assertEquals(message, result.getMessage());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testMarkAsRead() {
        Long notificationId = 4L;
        Notification notification = new Notification(NotificationType.COURSE_UPDATES, "Mark as Read", 1L);

        when(notificationRepository.findById(notificationId))
                .thenReturn(Optional.of(notification));

        notificationService.markAsRead(notificationId);

        assertTrue(notification.isRead());
        verify(notificationRepository, times(1)).findById(notificationId);
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void testDeleteNotification() {
        Long notificationId = 5L;
        Notification notification = new Notification(NotificationType.COURSE_UPDATES, "Delete Notification", 1L);

        when(notificationRepository.findById(notificationId))
                .thenReturn(Optional.of(notification));

        notificationService.deleteNotification(notificationId);

        verify(notificationRepository, times(1)).findById(notificationId);
        verify(notificationRepository, times(1)).delete(notification);
    }

}