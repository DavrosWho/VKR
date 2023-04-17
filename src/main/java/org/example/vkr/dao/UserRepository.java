package org.example.vkr.dao;

import org.mapstruct.control.MappingControl;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    boolean existsByUsernameContaining(String username);
}