package org.example.vkr.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.vkr.models.Project;
import org.example.vkr.service.ProjectService;
import org.example.vkr.service.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final TokenService tokenService;

    @GetMapping
    public List<Project> getAllProjects(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        return projectService.getAllProjectsByUsername(tokenService.getUsernameByToken(token));
    }

    @PostMapping("/{name}")
    public void addProject(@PathVariable String name, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Project project = new Project(name);
        projectService.addProject(tokenService.getUsernameByToken(token), project);
    }

    @PutMapping("/{id}/{name}")
    public void editProject(@PathVariable Long id, @PathVariable String name, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        projectService.editProjectName(tokenService.getIdByToken(token), id, name);
    }

    @PostMapping("/addUser/{id}/{username}/{title}")
    public void addUserToProject(@PathVariable Long id, @PathVariable String username,
                                 @PathVariable int title, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        projectService.addUserToProject(tokenService.getIdByToken(token), username, id, title);
    }

    @DeleteMapping("/deleteUser/{id}/{username}")
    public void deleteUserFromProject(@PathVariable Long id, @PathVariable String username, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        projectService.deleteUserFromProject(tokenService.getIdByToken(token), username, id);
    }

    @DeleteMapping("/{userId}/{projectId}")
    public void deleteProject(@PathVariable Long userId, @PathVariable Long projectId) { projectService.deleteProject(userId, projectId); }
}