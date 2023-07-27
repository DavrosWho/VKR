package org.example.vkr.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByModuleId(Long moduleId);
}