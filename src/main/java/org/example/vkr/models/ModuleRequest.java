package org.example.vkr.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ModuleRequest {
    int status;
    int priority;
    String name;
    String description;
}
