package org.example.vkr.service;

import org.example.vkr.models.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllCommentsByModuleId(Long moduleId);
    void addComment(Long userId, Long moduleId, Comment comment);
    void deleteComment(Long userId, Long commentId);
    void editCommentText(Long userId, Long commentId, String text);
}