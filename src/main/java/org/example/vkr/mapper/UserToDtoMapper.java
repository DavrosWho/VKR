package org.example.vkr.mapper;

import org.example.vkr.models.*;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserToDtoMapper {
    User EditUserRequestToUser(Long id, Set<UserProject> projects, Set<UserModule> modules, Set<Comment> comments, UserRequest userRequest);
}