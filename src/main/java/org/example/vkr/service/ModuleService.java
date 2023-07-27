package org.example.vkr.service;

import org.example.vkr.models.Module;

import java.time.LocalDateTime;
import java.util.List;

public interface ModuleService {
    List<Module> getAllModulesByProjectId(Long projectId);

    void addUserToModule(Long requestUserId, String username, Long moduleId, int title);

    void deleteUserFromModule(Long requestUserId, String username, Long moduleId);

    void addModule(Long userId, Long projectId, Module module);
    void deleteModule(Long userId, Long moduleId);
    void editModuleStartDate(Long userId, Long moduleId, LocalDateTime startDate);
    void editModuleEndDate(Long userId, Long moduleId, LocalDateTime endDate);
    void editModuleStatus(Long userId, Long moduleId, int status);
    void editModulePriority(Long userId, Long moduleId, int priority);
    void editModuleName(Long userId, Long moduleId, String name);
    void editModuleDescription(Long userId, Long moduleId, String description);
}