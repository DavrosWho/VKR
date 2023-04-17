package org.example.vkr.mapper;

import org.example.vkr.models.User;
import org.example.vkr.models.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserToDtoMapper {
    User AddUserRequestToUser(UserRequest userRequest);
}