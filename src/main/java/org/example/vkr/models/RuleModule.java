package org.example.vkr.models;

import lombok.Data;

@Data
public class RuleModule {
    Long id;
    Rule rule;
    Module module;
    boolean isSource;
}