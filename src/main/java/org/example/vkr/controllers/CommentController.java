package org.example.vkr.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.vkr.models.Comment;
import org.example.vkr.models.CommentRequest;
import org.example.vkr.service.CommentService;
import org.example.vkr.service.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final TokenService tokenService;


    @GetMapping("/{moduleId}")
    public List<Comment> getAllComments(@PathVariable Long moduleId) {
        return commentService.getAllCommentsByModuleId(moduleId);
    }

    @PostMapping("/{moduleId}")
    public void addComment(@PathVariable Long moduleId,
                          @RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Comment comment = new Comment(commentRequest.getText());
        commentService.addComment(tokenService.getIdByToken(token), moduleId, comment);
    }

    @PutMapping("/{id}")
    public void editCommentText(@PathVariable Long id, @RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        commentService.editCommentText(tokenService.getIdByToken(token), id, commentRequest.getText());
    }

    @DeleteMapping("/{commentId}")
    public void deleteModule(@PathVariable Long commentId, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        commentService.deleteComment(tokenService.getIdByToken(token), commentId);
    }
}