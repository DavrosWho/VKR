package org.example.vkr.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.example.vkr.dao.CommentEntity;
import org.example.vkr.dao.ProjectEntity;
import org.example.vkr.dao.UserModuleEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class Module {
    Long id;
    Project project;
    Set<UserModule> users;
    Set<Comment> comments;
    int status;
    int priority;
    String name;
    String description;
    LocalDateTime startDate;
    LocalDateTime endDate;

    public Module(int status, int priority, String name, String description) {
        this.status = status;
        this.priority = priority;
        this.name = name;
        this.description = description;
    }
}
