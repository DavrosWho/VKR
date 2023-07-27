package org.example.vkr.service;

import org.example.vkr.models.Rule;
import org.example.vkr.models.RuleRequest;

import java.util.List;

public interface RuleService {
    List<Rule> getAllRulesByModuleId(Long moduleId);
    void addRule(Long userId, Long moduleId, Rule rule);

    void addRuleModule(Long ruleId, Long moduleId);

    void deleteRule(Long userId, Long ruleId);
}