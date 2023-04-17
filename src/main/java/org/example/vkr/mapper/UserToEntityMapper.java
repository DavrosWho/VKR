package org.example.vkr.mapper;

import org.example.vkr.dao.UserEntity;
import org.example.vkr.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserToEntityMapper {
    UserEntity userToUserEntity(User user);
    User userEntityToUser(UserEntity userEntity);
}
