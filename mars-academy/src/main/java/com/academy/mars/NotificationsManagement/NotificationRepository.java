package com.academy.mars.NotificationsManagement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByReceiverId(Long receiverID);
    List<Notification> findByReceiverIdAndReadFalse(Long receiverID);
}
