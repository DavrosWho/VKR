package org.example.vkr.models;

import lombok.Data;

@Data
public class UserProject {
    Long id;
    User user;
    Project project;
    int title;
}