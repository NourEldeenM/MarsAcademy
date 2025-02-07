package com.academy.mars.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,String> {
    List<Notification> findByReceiverId(String receiverID);
    List<Notification> findByReceiverIdAndReadFalse(String receiverID);
}
