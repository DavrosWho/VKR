package org.example.vkr.service;

import lombok.RequiredArgsConstructor;
import org.example.vkr.dao.*;
import org.example.vkr.exception.UserNotFoundException;
import org.example.vkr.mapper.ProjectToEntityMapper;
import org.example.vkr.models.Project;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultProjectService implements ProjectService{
    private final ProjectRepository projectRepository;
    private final UserProjectRepository userProjectRepository;
    private final UserRepository userRepository;
    private final ProjectToEntityMapper mapper;

    private void isUserCreator(Long userId, Long projectId) {
        Optional<UserProjectEntity> userProjectEntity = userProjectRepository.findByUserIdAndProjectIdAndTitle(userId, projectId, 0);
        if (!userProjectEntity.isPresent())
            throw new UserNotFoundException("There's no admin with id " + userId + " for project with id " + projectId);
    }

    private void isUserCreatorOrAdmin(Long userId, Long projectId) {
        Optional<UserProjectEntity> userProjectEntity0 = userProjectRepository.findByUserIdAndProjectIdAndTitle(userId, projectId, 0);
        Optional<UserProjectEntity> userProjectEntity1 = userProjectRepository.findByUserIdAndProjectIdAndTitle(userId, projectId, 1);
        if (!userProjectEntity0.isPresent() && !userProjectEntity1.isPresent())
            throw new UserNotFoundException("There's no admin with id " + userId + " for project with id " + projectId);
    }

    @Override
    public List<Project> getAllProjectsByUsername(String username) {
        Long userId = userRepository.findByUsername(username).get().getId();
        List<UserProjectEntity> userProjects = userProjectRepository.findAllByUserId(userId);
        ArrayList<ProjectEntity> projects = new ArrayList<ProjectEntity>();
        
        for (int i = 0; i < userProjects.size(); i++) {
            projects.add(userProjects.get(i).getProject());
        }

        Iterable<ProjectEntity> iterable = projects;

        return StreamSupport.stream(projects.spliterator(), false)
                .map(mapper::projectEntityToProject)
                .collect(Collectors.toList());
    }

    @Override
    public void addProject(String username, Project project) {
        //isUserCreator(userId, project.getId());
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (!userEntity.isPresent())
            throw new UserNotFoundException("User not found: username = " + username);
        ProjectEntity projectEntity = mapper.projectToProjectEntity(project);
        projectRepository.save(projectEntity);
        userProjectRepository.save(new UserProjectEntity(userEntity.get(), projectEntity, 0));
    }

    @Override
    public void addUserToProject(Long requestUserId, String username, Long projectId, int title) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
        if (!userEntity.isPresent())
            throw new UserNotFoundException("User not found: username = " + username);
        if (userProjectRepository.findByUserIdAndProjectId(userEntity.get().getId(), projectId).isPresent())
            throw new UserNotFoundException("This user is already a participant");
        isUserCreator(requestUserId, projectId);
        UserProjectEntity userProjectEntity = new UserProjectEntity(userEntity.get(), projectEntity.get(), title);
        userProjectRepository.save(userProjectEntity);
    }

    @Override
    public void deleteUserFromProject(Long requestUserId, String username, Long projectId) {
        Long userId = userRepository.findByUsername(username).get().getId();
        isUserCreator(requestUserId, projectId);
        UserProjectEntity userProjectEntity = userProjectRepository
                .findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new UserNotFoundException("UserProject not found: userId = " + userId + " projectId = " + projectId));
        userProjectRepository.delete(userProjectEntity);
    }

    @Override
    public void deleteProject(Long userId, Long projectId) {
        isUserCreator(userId, projectId);
        ProjectEntity projectEntity = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new UserNotFoundException("Project not found: id = " + projectId));
        projectRepository.delete(projectEntity);
    }

    @Override
    public void editProjectName(Long userId, Long projectId, String name) {
        isUserCreatorOrAdmin(userId, projectId);
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
        if (!projectEntity.isPresent())
            throw new UserNotFoundException("Project not found: id = " + projectId);

        projectEntity.get().setName(name);
        projectRepository.save(projectEntity.get());
    }
}