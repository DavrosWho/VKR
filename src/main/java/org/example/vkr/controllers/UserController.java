package org.example.vkr.controllers;

import org.example.vkr.mapper.UserToDtoMapper;
import org.example.vkr.models.User;
import org.example.vkr.models.UserRequest;
import org.example.vkr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserToDtoMapper mapper;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void addUser(@RequestBody UserRequest request) {
        userService.addUser(mapper.AddUserRequestToUser(request));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) { userService.deleteUser(id); }

    @GetMapping("/getByUsername/{username}")
    public boolean isExistUsername(@PathVariable String username) { return userService.isExistByUsername(username); }
}