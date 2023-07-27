package org.example.vkr.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    Long id;
    Module module;
    User user;
    String text;
    LocalDateTime writtenAt;

    public Comment(String text) {
        this.text= text;
    }
}