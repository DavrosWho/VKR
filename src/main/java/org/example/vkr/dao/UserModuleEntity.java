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
@Table(name = "user_module")
public class UserModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private ModuleEntity module;

    @Column(name = "title")
    private int title;


    public UserModuleEntity(UserEntity user, ModuleEntity module, int title) {
        this.user = user;
        this.module = module;
        this.title = title;
    }

    public UserModuleEntity(int title) {
        this.title = title;
    }
}