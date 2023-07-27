package org.example.vkr.models;

import lombok.Data;

import java.util.Set;

@Data
public class Rule {
    Long id;
    User user;
    Set<RuleModule> modules;
    int type;
    Boolean isDone;
    public Rule (int type) {
        this.isDone = false;
        this.type = type;
    }
}