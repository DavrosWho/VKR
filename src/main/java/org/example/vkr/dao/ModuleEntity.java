package org.example.vkr.dao;

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
@Table(name = "modules")
public class ModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int status;
    private int priority;
    private String name;
    private String description;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @PrePersist
    public void beforePersist() {
        startDate = LocalDateTime.now(ClockHolder.getClock());
        endDate = startDate;
    }
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(name="project_id", nullable=false)
    private ProjectEntity project;
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private Set<UserModuleEntity> users = new HashSet<UserModuleEntity>();
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private Set<CommentEntity> comments = new HashSet<CommentEntity>();

    public ModuleEntity (Long id) {
        this.id = id;
    }
    /*
    @JsonIgnore
    @ManyToMany(mappedBy = "relatedProjects")
    private Set<UserEntity> relatedUsers = new HashSet<>();
     */
}


