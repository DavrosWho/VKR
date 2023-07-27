package org.example.vkr.models;

import lombok.Data;

@Data
public class Notification {
    Long id;
    Module module;
    User user;
    int type;

    public Notification(int type) {
        this.type = type;
    }
}

