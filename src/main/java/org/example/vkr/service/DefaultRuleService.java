package org.example.vkr.service;

import lombok.RequiredArgsConstructor;
import org.example.vkr.dao.*;
import org.example.vkr.exception.UserNotFoundException;
import org.example.vkr.mapper.RuleToEntityMapper;
import org.example.vkr.models.Rule;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultRuleService implements RuleService {

    private final RuleRepository ruleRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final RuleModuleRepository ruleModuleRepository;
    private final RuleToEntityMapper mapper;

    @Override
    public List<Rule> getAllRulesByModuleId(Long moduleId) {
        List<RuleModuleEntity> ruleModules = ruleModuleRepository.findAllByModuleIdAndIsSource(moduleId, true);
        ArrayList<RuleEntity> rules = new ArrayList<RuleEntity>();
        RuleEntity ruleEntity = null;
        for (int i = 0; i < ruleModules.size(); i++) {
            ruleEntity = ruleModules.get(i).getRule();
            ruleEntity.setModules(new HashSet<>());
            /*
            Optional<RuleModuleEntity> ruleModuleEntity = ruleModuleRepository.findByRuleIdAndModuleId(ruleEntity.getId(), moduleId);
            ruleEntity.getModules().add(new RuleModuleEntity(ruleModuleEntity.get().getId(), ruleModuleEntity.get().getRule(),
                    ruleModuleEntity.get().getModule(), ruleModuleEntity.get()
             */
            RuleModuleEntity ruleModuleEntity = new RuleModuleEntity(ruleModuleRepository.findByRuleIdAndModuleId(ruleEntity.getId(), moduleId).get().getModule());
            //ruleModuleEntity.setModule(new ModuleEntity());
            ruleEntity.getModules().add(ruleModuleEntity);
            List<RuleModuleEntity> ruleModulesNoSource = ruleModuleRepository.findAllByRuleIdAndIsSource(ruleEntity.getId(), false);
            for (int j = 0; j < ruleModulesNoSource.size(); j++) {
                //ruleModulesNoSource.get(j).setModule(ruleModulesNoSource);
                ruleEntity.getModules().add(new RuleModuleEntity(ruleModulesNoSource.get(j).getModule()));
            }
            rules.add(ruleEntity);
        }

        return StreamSupport.stream(rules.spliterator(), false)
                .map(mapper::ruleEntityToRule)
                .collect(Collectors.toList());
    }

    @Override
    public void addRule(Long userId, Long moduleId, Rule rule) {
        //isUserCreatorOrAdminForProject(userId, projectId); TODO: check is user a creator of rule
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.isPresent())
            throw new UserNotFoundException("User not found: id = " + userId);
        Optional<ModuleEntity> moduleEntity = moduleRepository.findById(moduleId);
        if (!moduleEntity.isPresent())
            throw new UserNotFoundException("Module not found: id = " + moduleId);

        RuleEntity ruleEntity = mapper.ruleToRuleEntity(rule);
        ruleEntity.setUser(userEntity.get());
        ruleRepository.save(ruleEntity);
        ruleModuleRepository.save(new RuleModuleEntity(ruleEntity, moduleEntity.get(), true));
    }

    @Override
    public void addRuleModule(Long ruleId, Long moduleId) {
        Optional<RuleEntity> ruleEntity = ruleRepository.findById(ruleId);
        if (!ruleEntity.isPresent())
            throw new UserNotFoundException("Rule not found: id = " + ruleId);
        Optional<ModuleEntity> moduleEntity = moduleRepository.findById(moduleId);
        if (!moduleEntity.isPresent())
            throw new UserNotFoundException("Module not found: id = " + moduleId);
        ruleModuleRepository.save(new RuleModuleEntity(ruleEntity.get(), moduleEntity.get(), false));
    }

    @Override
    public void deleteRule(Long userId, Long ruleId) {
        RuleEntity ruleEntity = ruleRepository
                .findById(ruleId)
                .orElseThrow(() -> new UserNotFoundException("Rule not found: id = " + ruleId));
        List<RuleModuleEntity> ruleModuleEntities = ruleModuleRepository.findAllByRuleId(ruleId);
        ruleModuleRepository.deleteAll(ruleModuleEntities);
        ruleRepository.delete(ruleEntity);
    }

    /* addmodules
    ruleModuleRepository.findByRuleIdAndModuleId();
        ruleEntity = ruleRepository.fin()
        for (int i = 0; i < ruleRequest.getModulesId().size(); i++) {
            RuleModuleEntity ruleModuleEntity = new RuleModuleEntity();
            ruleEntity.getModules().add(new);
        }
        ruleEntity.set(projectEntity.get());
        moduleRepository.save(moduleEntity);
        userModuleRepository.save(new UserModuleEntity(userEntity.get(), moduleEntity, 0));
     */
}
