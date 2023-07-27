package org.example.vkr.mapper;

import org.example.vkr.dao.NotificationEntity;
import org.example.vkr.models.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationToEntityMapper {
    NotificationEntity notificationToNotificationEntity(Notification notification);
    Notification notificationEntityToNotification(NotificationEntity notificationEntity);
}
