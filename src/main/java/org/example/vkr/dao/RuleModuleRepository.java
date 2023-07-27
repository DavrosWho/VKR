package org.example.vkr.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RuleModuleRepository extends CrudRepository<RuleModuleEntity, Long> {
    Optional<RuleModuleEntity> findByRuleIdAndModuleId(long ruleId, long moduleId);
    List<RuleModuleEntity> findAllByRuleIdAndIsSource(long ruleId, boolean isSource);
    List<RuleModuleEntity> findAllByRuleId(long ruleId);
    List<RuleModuleEntity> findAllByModuleIdAndIsSource(long moduleId, boolean isSource);
}
