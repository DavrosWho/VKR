package org.example.vkr.service;

import lombok.RequiredArgsConstructor;
import org.example.vkr.dao.UserEntity;
import org.example.vkr.dao.UserRepository;
import org.example.vkr.exceprion.UserNotFoundException;
import org.example.vkr.mapper.UserToEntityMapper;
import org.example.vkr.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService{
    private final UserRepository userRepository;
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
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: id = " + id));
        userRepository.delete(userEntity);
    }

    @Override
    public boolean isExistByUsername(String username) {
        return userRepository
                .existsByUsernameContaining(username);
    }

}
