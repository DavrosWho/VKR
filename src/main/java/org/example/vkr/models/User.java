package org.example.vkr.models;

import lombok.Value;

@Value
public class User {
    Long id;
    String username;
    String password;
    String email;
}
