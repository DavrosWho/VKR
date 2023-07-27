package org.example.vkr.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserModuleRepository extends CrudRepository<UserModuleEntity, Long> {
    Optional<UserModuleEntity> findByUserIdAndModuleIdAndTitle(long userId, long moduleId, int title);
    Optional<UserModuleEntity> findByUserIdAndModuleId(long userId, long moduleId);

    List<UserModuleEntity> findAllByModuleId(Long moduleId);
}
