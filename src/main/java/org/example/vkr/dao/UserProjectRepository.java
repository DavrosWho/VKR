package org.example.vkr.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserProjectRepository extends CrudRepository<UserProjectEntity, Long> {
    Optional<UserProjectEntity> findByUserIdAndProjectIdAndTitle(long userId, long projectId, int title);
    Optional<UserProjectEntity> findByUserIdAndProjectId(long userId, long projectId);
    List<UserProjectEntity> findAllByUserId(Long userId);
    List<UserProjectEntity> findAllByProjectId(Long projectId);
}