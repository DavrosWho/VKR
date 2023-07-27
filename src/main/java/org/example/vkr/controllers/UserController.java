package org.example.vkr.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.vkr.mapper.UserToDtoMapper;
import org.example.vkr.models.User;
import org.example.vkr.models.UserRequest;
import org.example.vkr.service.TokenService;
import org.example.vkr.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:9000")
@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserToDtoMapper mapper;
    private final TokenService tokenService;

    @GetMapping("/getAllByProjectId/{id}")
    public List<User> getAllUsersByProjectId(@PathVariable Long id) {
        return userService.getAllUsersByProjectId(id);
    }

    @GetMapping("/getAllByModuleId/{id}")
    public List<User> getAllUsersByModuleId(@PathVariable Long id) {
        return userService.getAllUsersByModuleId(id);
    }

    @GetMapping
    public User getUserById(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        return userService.getUserById(tokenService.getIdByToken(token));
    }

    //@GetMapping
    //public List<User> getAllUsers() {
    //    return userService.getAllUsers();
    //}

    //@PostMapping
    //public void addUser(@RequestBody UserRequest request) {
    //    userService.addUser(mapper.AddUserRequestToUser(request));
    //}

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) { userService.deleteUser(id); }

    @GetMapping("/getByUsername/{username}")
    public boolean isExistUsername(@PathVariable String username) { return userService.isExistByUsername(username); }

    @PutMapping("/editAvatarUrl")
    public void editModuleDescription(@RequestBody String avatarUrl, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        userService.editAvatarUrl(tokenService.getIdByToken(token), avatarUrl);
    }

    @PutMapping("/{id}")
    public void editUser(@PathVariable Long id, @RequestBody UserRequest request) {
        userService.editUser(mapper.EditUserRequestToUser(id, userService.getUserById(id).getProjects(),
                userService.getUserById(id).getModules(), userService.getUserById(id).getComments(), request));
    }
}