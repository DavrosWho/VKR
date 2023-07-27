package org.example.vkr.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    boolean existsByUsernameContaining(String username);
    boolean existsByEmailContaining(String email);
    Optional<UserEntity> findByUsername(String username);
}