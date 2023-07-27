package org.example.vkr.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int type;
    @ManyToOne
    @JoinColumn(name="module_id", nullable=false)
    private ModuleEntity module;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;

}
