package org.example.vkr.service;

import org.example.vkr.models.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    List<User> getAllUsers();
    //List<User> findByUsername(String username);
    void addUser(User user);
    boolean isExistByUsername(String username);
    void deleteUser(Long id);
    //void editUser(User user);
}
