package org.example.vkr.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Long> {
    List<NotificationEntity> findAllByUserId(Long userId);
}