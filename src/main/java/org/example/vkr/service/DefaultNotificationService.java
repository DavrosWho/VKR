package org.example.vkr.service;

import lombok.RequiredArgsConstructor;
import org.example.vkr.dao.*;
import org.example.vkr.exception.UserNotFoundException;
import org.example.vkr.mapper.NotificationToEntityMapper;
import org.example.vkr.models.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultNotificationService implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final NotificationToEntityMapper mapper;

    @Override
    public List<Notification> getAllNotificationsByUserId(Long userId) {
        Iterable<NotificationEntity> iterable = notificationRepository.findAllByUserId(userId);

        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::notificationEntityToNotification)
                .collect(Collectors.toList());
    }

    @Override
    public void addNotification(Long userId, Long moduleId, int type) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.isPresent())
            throw new UserNotFoundException("User not found: id = " + userId);
        Optional<ModuleEntity> moduleEntity = moduleRepository.findById(moduleId);
        if (!moduleEntity.isPresent())
            throw new UserNotFoundException("Module not found: id = " + moduleId);
        NotificationEntity notificationEntity = mapper.notificationToNotificationEntity(new Notification(type));
        notificationEntity.setUser(userEntity.get());
        notificationEntity.setModule(moduleEntity.get());
        notificationRepository.save(notificationEntity);
    }
}
