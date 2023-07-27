package org.example.vkr.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.vkr.models.Module;
import org.example.vkr.models.ModuleRequest;
import org.example.vkr.service.ModuleService;
import org.example.vkr.service.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/modules")
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;
    private final TokenService tokenService;

    //@GetMapping("/{id}")
    //public Project getProjectById(@PathVariable Long id) {
    //    return projectService.getProjectById(id);
    //}

    @GetMapping("/{projectId}")
    public List<Module> getAllModules(@PathVariable Long projectId) {
        return moduleService.getAllModulesByProjectId(projectId);
    }

    @PostMapping("/{projectId}")
    public void addModule(@PathVariable Long projectId,
                          @RequestBody ModuleRequest moduleRequest, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Module module = new Module(moduleRequest.getStatus(), moduleRequest.getPriority(),
                moduleRequest.getName(), moduleRequest.getDescription());
        moduleService.addModule(tokenService.getIdByToken(token), projectId, module);
    }

    @PutMapping("/name/{id}/{name}")
    public void editModuleName(@PathVariable Long id, @PathVariable String name, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        moduleService.editModuleName(tokenService.getIdByToken(token), id, name);
    }

    @PostMapping("/addUser/{id}/{username}/{title}")
    public void addUserToModule(@PathVariable Long id, @PathVariable String username,
                                @PathVariable int title, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        moduleService.addUserToModule(tokenService.getIdByToken(token), username, id, title);
    }

    @DeleteMapping("/deleteUser/{id}/{username}")
    public void deleteUserFromModule(@PathVariable Long id, @PathVariable String username, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        moduleService.deleteUserFromModule(tokenService.getIdByToken(token), username, id);
    }

    @PutMapping("/description/{id}/{description}")
    public void editModuleDescription(@PathVariable Long id, @PathVariable String description, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        moduleService.editModuleDescription(tokenService.getIdByToken(token), id, description);
    }

    @PutMapping("/status/{id}/{status}")
    public void editModuleStatus(@PathVariable Long id, @PathVariable int status, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        moduleService.editModuleStatus(tokenService.getIdByToken(token), id, status);
    }

    @PutMapping("/priority/{id}/{priority}")
    public void editModulePriority(@PathVariable Long id, @PathVariable int priority, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        moduleService.editModulePriority(tokenService.getIdByToken(token), id, priority);
    }

    @PutMapping("/endDate/{id}/{endDate}")
    public void editModuleEndDate(@PathVariable Long id, @PathVariable LocalDateTime endDate, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        moduleService.editModuleEndDate(tokenService.getIdByToken(token), id, endDate);
    }

    @DeleteMapping("/{moduleId}")
    public void deleteModule(@PathVariable Long moduleId, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        moduleService.deleteModule(tokenService.getIdByToken(token), moduleId);
    }
}