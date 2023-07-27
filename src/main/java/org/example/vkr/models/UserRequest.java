package org.example.vkr.models;

import lombok.Data;

import java.util.Set;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String avatarUrl;
    private String about;
    private String hash;
    private Boolean isAdmin;
}
