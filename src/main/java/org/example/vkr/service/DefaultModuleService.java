package org.example.vkr.service;

import lombok.RequiredArgsConstructor;
import org.example.vkr.dao.*;
import org.example.vkr.exception.UserNotFoundException;
import org.example.vkr.mapper.ModuleToEntityMapper;
import org.example.vkr.models.Module;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultModuleService implements ModuleService{
    private final ModuleRepository moduleRepository;
    private final ProjectRepository projectRepository;
    private final UserModuleRepository userModuleRepository;
    private final UserProjectRepository userProjectRepository;
    private final UserRepository userRepository;
    private final ModuleToEntityMapper mapper;

    private final RuleRepository ruleRepository;
    private final RuleModuleRepository ruleModuleRepository;

    private final NotificationService notificationService;

    private void isUserCreatorOrAdminForProject(Long userId, Long projectId) {
        Optional<UserProjectEntity> userProjectEntity0 = userProjectRepository.findByUserIdAndProjectIdAndTitle(userId, projectId, 0);
        Optional<UserProjectEntity> userProjectEntity1 = userProjectRepository.findByUserIdAndProjectIdAndTitle(userId, projectId, 1);
        if (!userProjectEntity0.isPresent() && !userProjectEntity1.isPresent())
            throw new UserNotFoundException("There's no admin or creator with id " + userId + " for project with id " + projectId);
    }

    private void isUserCreatorOrAdmin(Long userId, Long moduleId) {
        Optional<UserModuleEntity> userModuleEntity0 = userModuleRepository.findByUserIdAndModuleIdAndTitle(userId, moduleId, 0);
        Optional<UserModuleEntity> userModuleEntity1 = userModuleRepository.findByUserIdAndModuleIdAndTitle(userId, moduleId, 1);
        if (!userModuleEntity0.isPresent() && !userModuleEntity1.isPresent())
            throw new UserNotFoundException("There's no admin or creator with id " + userId + " for module with id " + moduleId);
    }

    @Override
    public List<Module> getAllModulesByProjectId(Long projectId) {
        Iterable<ModuleEntity> iterable = moduleRepository.findAllByProjectId(projectId);
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::moduleEntityToModule)
                .collect(Collectors.toList());
    }

    @Override
    public void addUserToModule(Long requestUserId, String username, Long moduleId, int title) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        Optional<ModuleEntity> moduleEntity = moduleRepository.findById(moduleId);
        if (!userEntity.isPresent())
            throw new UserNotFoundException("User not found: username = " + username);
        if (userModuleRepository.findByUserIdAndModuleId(userEntity.get().getId(), moduleId).isPresent())
            throw new UserNotFoundException("This user is already a participant");
        isUserCreatorOrAdmin(requestUserId, moduleId);
        UserModuleEntity userModuleEntity = new UserModuleEntity(userEntity.get(), moduleEntity.get(), title);
        userModuleRepository.save(userModuleEntity);
    }

    @Override
    public void deleteUserFromModule(Long requestUserId, String username, Long moduleId) {
        Long userId = userRepository.findByUsername(username).get().getId();
        isUserCreatorOrAdmin(requestUserId, moduleId);
        UserModuleEntity userModuleEntity = userModuleRepository
                .findByUserIdAndModuleId(userId, moduleId)
                .orElseThrow(() -> new UserNotFoundException("UserProject not found: userId = " + userId + " moduleId = " + moduleId));
        userModuleRepository.delete(userModuleEntity);
    }

    @Override
    public void addModule(Long userId, Long projectId, Module module) {
        isUserCreatorOrAdminForProject(userId, projectId);
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.isPresent())
            throw new UserNotFoundException("User not found: id = " + userId);
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
        if (!projectEntity.isPresent())
            throw new UserNotFoundException("Project not found: id = " + projectId);

        ModuleEntity moduleEntity = mapper.moduleToModuleEntity(module);
        moduleEntity.setProject(projectEntity.get());
        moduleRepository.save(moduleEntity);
        userModuleRepository.save(new UserModuleEntity(userEntity.get(), moduleEntity, 0));
    }

    @Override
    public void deleteModule(Long userId, Long moduleId) {
        isUserCreatorOrAdmin(userId, moduleId);
        ModuleEntity moduleEntity = moduleRepository
                .findById(moduleId)
                .orElseThrow(() -> new UserNotFoundException("Module not found: id = " + moduleId));
        Optional<UserModuleEntity> userModuleEntity = userModuleRepository.findByUserIdAndModuleId(userId, moduleId);
        userModuleRepository.delete(userModuleEntity.get());
        moduleRepository.delete(moduleEntity);
    }

    @Override
    public void editModuleStartDate(Long userId, Long moduleId, LocalDateTime startDate) {
        isUserCreatorOrAdmin(userId, moduleId);
        Optional<ModuleEntity> moduleEntity = moduleRepository.findById(moduleId);
        if (!moduleEntity.isPresent())
            throw new UserNotFoundException("Module not found: id = " + moduleId);

        moduleEntity.get().setStartDate(startDate);
        moduleRepository.save(moduleEntity.get());
    }

    @Override
    public void editModuleEndDate(Long userId, Long moduleId, LocalDateTime endDate) {
        isUserCreatorOrAdmin(userId, moduleId);
        Optional<ModuleEntity> moduleEntity = moduleRepository.findById(moduleId);
        if (!moduleEntity.isPresent())
            throw new UserNotFoundException("Module not found: id = " + moduleId);

        moduleEntity.get().setEndDate(endDate);
        moduleRepository.save(moduleEntity.get());
    }

    @Override
    public void editModuleStatus(Long userId, Long moduleId, int status) {
        isUserCreatorOrAdmin(userId, moduleId);
        Optional<ModuleEntity> moduleEntity = moduleRepository.findById(moduleId);
        if (!moduleEntity.isPresent())
            throw new UserNotFoundException("Module not found: id = " + moduleId);

        moduleEntity.get().setStatus(status);
        moduleRepository.save(moduleEntity.get());

        List<RuleModuleEntity> ruleModules = ruleModuleRepository.findAllByModuleIdAndIsSource(moduleId, true);
        List<UserModuleEntity> userModuleEntityList = null;
        if (status == 2) {
            for (int i = 0; i < ruleModules.size(); i++) {
                if (ruleModules.get(i).getRule().getType() == 0 && !ruleModules.get(i).getRule().getIsDone()) {
                    List<RuleModuleEntity> ruleModulesNotSource = ruleModuleRepository
                            .findAllByRuleIdAndIsSource(ruleModules.get(i).getRule().getId(), false);
                    ruleModules.get(i).getRule().setIsDone(true);
                    for (int j = 0; j < ruleModulesNotSource.size(); j++) {
                        ruleModulesNotSource.get(j).getModule().setStatus(1);
                        ruleModuleRepository.save(ruleModulesNotSource.get(j));
                        userModuleEntityList =
                                userModuleRepository.findAllByModuleId(ruleModulesNotSource.get(j).getModule().getId());
                        for (int k = 0; k < userModuleEntityList.size(); k++) {
                            notificationService.addNotification(userModuleEntityList.get(k).getUser().getId(),
                                    userModuleEntityList.get(k).getModule().getId(), 0);
                        }
                    }
                }
                ruleRepository.save(ruleModules.get(i).getRule());
            }
        }
    }

    @Override
    public void editModulePriority(Long userId, Long moduleId, int priority) {
        isUserCreatorOrAdmin(userId, moduleId);
        Optional<ModuleEntity> moduleEntity = moduleRepository.findById(moduleId);
        if (!moduleEntity.isPresent())
            throw new UserNotFoundException("Module not found: id = " + moduleId);

        moduleEntity.get().setPriority(priority);
        moduleRepository.save(moduleEntity.get());
    }

    @Override
    public void editModuleName(Long userId, Long moduleId, String name) {
        isUserCreatorOrAdmin(userId, moduleId);
        Optional<ModuleEntity> moduleEntity = moduleRepository.findById(moduleId);
        if (!moduleEntity.isPresent())
            throw new UserNotFoundException("Module not found: id = " + moduleId);

        moduleEntity.get().setName(name);
        moduleRepository.save(moduleEntity.get());
    }

    @Override
    public void editModuleDescription(Long userId, Long moduleId, String description) {
        isUserCreatorOrAdmin(userId, moduleId);
        Optional<ModuleEntity> moduleEntity = moduleRepository.findById(moduleId);
        if (!moduleEntity.isPresent())
            throw new UserNotFoundException("Module not found: id = " + moduleId);

        moduleEntity.get().setDescription(description);
        moduleRepository.save(moduleEntity.get());
    }
}