package org.example.vkr.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.vkr.dao.ModuleEntity;
import org.example.vkr.dao.UserEntity;
import org.example.vkr.dao.UserProjectEntity;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Project {
    Long id;
    String name;
    LocalDateTime createdAt;
    Set<UserProject> users;
    Set<Module> modules;

    public Project(String name) {
        this.name = name;
    }
}