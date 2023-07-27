package org.example.vkr.service;

import lombok.RequiredArgsConstructor;
import org.example.vkr.dao.*;
import org.example.vkr.exception.UserNotFoundException;
import org.example.vkr.mapper.CommentToEntityMapper;
import org.example.vkr.models.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultCommentService implements CommentService{
    private final CommentRepository commentRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final CommentToEntityMapper mapper;

    @Override
    public List<Comment> getAllCommentsByModuleId(Long moduleId) {
        Iterable<CommentEntity> iterable = commentRepository.findAllByModuleId(moduleId);

        /*
        List<CommentEntity> iterable = commentRepository.findAllByModuleId(moduleId);
        ArrayList<CommentEntity> comments = new ArrayList<CommentEntity>();
        CommentEntity commentEntity = null;
        for (int i = 0; i < iterable.size(); i++) {
            commentEntity = iterable.get(i);
            commentEntity.setUser(userRepository.findById(commentEntity.));
            userEntity.setModules(new HashSet<>());
            userEntity.getModules().add(new UserModuleEntity(userModuleRepository.findByUserIdAndModuleId(userEntity.getId(), id).get().getTitle()));
            comments.add(commentEntity);
        }

        return StreamSupport.stream(users.spliterator(), false)
                .map(mapper::userEntityToUser)
                .collect(Collectors.toList());
        */

        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::commentEntityToComment)
                .collect(Collectors.toList());
    }

    @Override
    public void addComment(Long userId, Long moduleId, Comment comment) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.isPresent())
            throw new UserNotFoundException("User not found: id = " + userId);
        Optional<ModuleEntity> moduleEntity = moduleRepository.findById(moduleId);
        if (!moduleEntity.isPresent())
            throw new UserNotFoundException("Module not found: id = " + moduleId);
        CommentEntity commentEntity = mapper.commentToCommentEntity(comment);
        commentEntity.setUser(userEntity.get());
        commentEntity.setModule(moduleEntity.get());
        commentRepository.save(commentEntity);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        CommentEntity commentEntity = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new UserNotFoundException("Comment not found: id = " + commentId));
        if (!Objects.equals(commentEntity.getUser().getId(), userId))
            throw new UserNotFoundException("User with id " + userId + " is not a creator for comment with id " + commentId);
        commentRepository.delete(commentEntity);
    }

    @Override
    public void editCommentText(Long userId, Long commentId, String text) {
        Optional<CommentEntity> commentEntity = commentRepository.findById(commentId);
        if (!commentEntity.isPresent())
            throw new UserNotFoundException("Comment not found: id = " + commentId);
        if (!Objects.equals(commentEntity.get().getUser().getId(), userId))
            throw new UserNotFoundException("User with id " + userId + " is not a creator for comment with id " + commentId);

        commentEntity.get().setText(text);
        commentRepository.save(commentEntity.get());
    }
}