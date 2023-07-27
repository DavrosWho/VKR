package org.example.vkr.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.vkr.models.Module;
import org.example.vkr.models.ModuleRequest;
import org.example.vkr.models.Rule;
import org.example.vkr.service.RuleService;
import org.example.vkr.service.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RuleController {

    private final RuleService ruleService;
    private final TokenService tokenService;

    @GetMapping("/{moduleId}")
    public List<Rule> getAllRulesByModuleId(@PathVariable Long moduleId) {
        return ruleService.getAllRulesByModuleId(moduleId);
    }

    @PostMapping("/{moduleId}/{type}")
    public void addModule(@PathVariable Long moduleId, @PathVariable int type, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        ruleService.addRule(tokenService.getIdByToken(token), moduleId, new Rule(type));
    }

    @PostMapping("/addRuleModule/{ruleId}/{moduleId}")
    public void addRuleModule(@PathVariable Long ruleId, @PathVariable Long moduleId) {
        ruleService.addRuleModule(ruleId, moduleId);
    }

    @DeleteMapping("/{ruleId}")
    public void deleteModule(@PathVariable Long ruleId, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        ruleService.deleteRule(tokenService.getIdByToken(token), ruleId);
    }
}
