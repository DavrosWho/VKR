package org.example.vkr.models;

import lombok.Data;

@Data
public class UserModule {
    Long id;
    User user;
    Module module;
    int title;
}

