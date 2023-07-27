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
    void deleteUserByUsername(String username);
    void editUser(User user);
    List<User> getAllUsersByProjectId(Long id);

    List<User> getAllUsersByModuleId(Long id);

    void editAvatarUrl(Long userId, String avatarUrl);
}
