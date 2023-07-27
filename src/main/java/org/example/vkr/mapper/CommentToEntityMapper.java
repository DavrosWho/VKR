package org.example.vkr.mapper;

import org.example.vkr.dao.CommentEntity;
import org.example.vkr.models.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentToEntityMapper {
    CommentEntity commentToCommentEntity(Comment comment);
    Comment commentEntityToComment(CommentEntity commentEntity);
}