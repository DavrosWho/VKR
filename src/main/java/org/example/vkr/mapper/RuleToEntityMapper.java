package org.example.vkr.mapper;

import org.example.vkr.dao.RuleEntity;
import org.example.vkr.models.Rule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RuleToEntityMapper {
    RuleEntity ruleToRuleEntity(Rule rule);
    Rule ruleEntityToRule(RuleEntity ruleEntity);
}