package org.example.vkr.dao;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_project")
public class UserProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @Column(name = "title")
    private int title;

    public UserProjectEntity(UserEntity user, ProjectEntity project, int title) {
        this.user = user;
        this.project = project;
        this.title = title;
    }

    public UserProjectEntity(int title) {
        this.title = title;
    }
}
