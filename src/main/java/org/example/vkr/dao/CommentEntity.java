package org.example.vkr.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vkr.clock.ClockHolder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(name="module_id", nullable=false)
    private ModuleEntity module;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;
    @Column(name = "written_at", updatable = false, nullable = false)
    private LocalDateTime writtenAt;
    @PrePersist
    public void beforePersist() {
        writtenAt = LocalDateTime.now(ClockHolder.getClock());
    }
}
