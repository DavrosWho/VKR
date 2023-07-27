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
@Table(name = "rules")
public class RuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int type;
    @Column(name = "is_done")
    private Boolean isDone;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;
    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
    private Set<RuleModuleEntity> modules = new HashSet<RuleModuleEntity>();
}
