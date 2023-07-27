package org.example.vkr.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vkr.clock.ClockHolder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void beforePersist() {
        createdAt = LocalDateTime.now(ClockHolder.getClock());
    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<UserProjectEntity> users = new HashSet<UserProjectEntity>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<ModuleEntity> modules = new HashSet<ModuleEntity>();

    /*
    @JsonIgnore
    @ManyToMany(mappedBy = "relatedProjects")
    private Set<UserEntity> relatedUsers = new HashSet<>();
     */
}