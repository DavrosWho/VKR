package org.example.vkr.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModuleRepository extends CrudRepository<ModuleEntity, Long> {
    List<ModuleEntity> findAllByProjectId(Long projectId);
}
