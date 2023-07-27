package org.example.vkr.service;

import lombok.RequiredArgsConstructor;
import org.example.vkr.dao.*;
import org.example.vkr.exception.UserNotFoundException;
import org.example.vkr.mapper.UserToEntityMapper;
import org.example.vkr.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService{
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ModuleRepository moduleRepository;
    private final UserProjectRepository userProjectRepository;
    private final UserModuleRepository userModuleRepository;
    private final UserToEntityMapper mapper;

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: id = " + id));

        return mapper.userEntityToUser(userEntity);
    }

    @Override
    public List<User> getAllUsers() {
        Iterable<UserEntity> iterable = userRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::userEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public void addUser(User user) {
        UserEntity userEntity = mapper.userToUserEntity(user);
        userRepository.save(userEntity);
    }

    @Override
    public void editUser(User user) {
        if (!userRepository.existsById(user.getId()))
            throw new UserNotFoundException("User not found: id = " + user.getId());

        UserEntity bookEntity = mapper.userToUserEntity(user);
        userRepository.save(bookEntity);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: id = " + id));
        userRepository.delete(userEntity);
    }

    @Override
    public void deleteUserByUsername(String username) {
        UserEntity userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: username = " + username));
        userRepository.delete(userEntity);
    }

    @Override
    public boolean isExistByUsername(String username) {
        return userRepository
                .existsByUsernameContaining(username);
    }

    @Override
    public List<User> getAllUsersByProjectId(Long id) {
        Long projectId = projectRepository.findById(id).get().getId();
        List<UserProjectEntity> userProjects = userProjectRepository.findAllByProjectId(projectId);
        ArrayList<UserEntity> users = new ArrayList<UserEntity>();
        UserEntity userEntity = null;
        for (int i = 0; i < userProjects.size(); i++) {
            userEntity = userProjects.get(i).getUser();
            userEntity.setProjects(new HashSet<>());
            userEntity.getProjects().add(new UserProjectEntity(userProjectRepository.findByUserIdAndProjectId(userEntity.getId(), id).get().getTitle()));
            users.add(userEntity);
        }

        return StreamSupport.stream(users.spliterator(), false)
                .map(mapper::userEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsersByModuleId(Long id) {
        Long moduleId = moduleRepository.findById(id).get().getId();
        List<UserModuleEntity> userModules = userModuleRepository.findAllByModuleId(moduleId);
        ArrayList<UserEntity> users = new ArrayList<UserEntity>();
        UserEntity userEntity = null;
        for (int i = 0; i < userModules.size(); i++) {
            userEntity = userModules.get(i).getUser();
            userEntity.setModules(new HashSet<>());
            userEntity.getModules().add(new UserModuleEntity(userModuleRepository.findByUserIdAndModuleId(userEntity.getId(), id).get().getTitle()));
            users.add(userEntity);
        }

        return StreamSupport.stream(users.spliterator(), false)
                .map(mapper::userEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public void editAvatarUrl(Long userId, String avatarUrl) {
        if (!userRepository.existsById(userId))
            throw new UserNotFoundException("User not found: id = " + userId);

        Optional<UserEntity> userEntity = userRepository.findById(userId);
        userEntity.get().setAvatarUrl(avatarUrl);
        userRepository.save(userEntity.get());
    }
}
