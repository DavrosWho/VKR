package org.example.vkr.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private String about;

    private String name;

    private String surname;

    private String hash;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserProjectEntity> projects = new HashSet<UserProjectEntity>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserModuleEntity> modules = new HashSet<UserModuleEntity>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<CommentEntity> comments = new HashSet<CommentEntity>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<RuleEntity> rules = new HashSet<RuleEntity>();
    /*@ManyToMany
    @JoinTable(name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")

    )
    private Set<ProjectEntity> relatedProjects = new HashSet<>();

     */
}
