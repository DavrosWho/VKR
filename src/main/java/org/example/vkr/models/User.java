package org.example.vkr.models;

import lombok.Data;

import java.util.Set;

@Data
public class User {
    Long id;
    String username;
    String password;
    String email;
    String avatarUrl;
    String about;
    String name;
    String surname;
    String hash;
    Boolean isAdmin;
    Set<UserProject> projects;
    Set<UserModule> modules;
    Set<Comment> comments;
    Set<Rule> rules;
}
