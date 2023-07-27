package org.example.vkr.service;

import org.example.vkr.models.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotificationsByUserId(Long userId);
    void addNotification(Long userId, Long moduleId, int type);
}
