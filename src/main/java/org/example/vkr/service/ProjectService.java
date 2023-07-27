package org.example.vkr.service;

import org.example.vkr.models.Project;

import java.util.List;

public interface ProjectService {
    void addProject(String username, Project project);

    void addUserToProject(Long requestUserId, String username, Long projectId, int title);

    void deleteUserFromProject(Long requestUserId, String username, Long projectId);

    void deleteProject(Long userId, Long projectId);
    void editProjectName(Long userId, Long projectId, String name);
    List<Project> getAllProjectsByUsername(String username);
}