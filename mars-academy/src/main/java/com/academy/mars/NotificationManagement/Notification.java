package com.academy.mars.NotificationManagement;

import com.academy.mars.UserManagement.User;

public abstract class Notification {
    protected String ID;
    protected String message;
    protected User receiver;
    protected boolean isRead;

    protected void markAsRead(){isRead = true;}
}
