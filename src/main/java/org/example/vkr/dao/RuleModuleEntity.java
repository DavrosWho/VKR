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
@Table(name = "rule_module")
public class RuleModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    private RuleEntity rule;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private ModuleEntity module;

    @Column(name = "is_source")
    private boolean isSource;

    public RuleModuleEntity(RuleEntity rule, ModuleEntity module, boolean isSource) {
        this.rule = rule;
        this.module = module;
        this.isSource = isSource;
    }

    public RuleModuleEntity(ModuleEntity module) {
        this.module = module;
    }

    public RuleModuleEntity(Long id) {
        this.id = id;
    }
}
