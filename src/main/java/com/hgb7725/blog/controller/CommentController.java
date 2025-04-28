package com.hgb7725.blog.controller;

import com.hgb7725.blog.payload.CommentDTO;
import com.hgb7725.blog.service.CommentService;
import com.hgb7725.blog.utils.BuildURIForController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{post_id}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable(name = "post_id") Long postId,
            @Valid @RequestBody CommentDTO commentDTO
    ) {
        CommentDTO comment = commentService.createComment(postId, commentDTO);
        URI uri = BuildURIForController.buildURIForController(comment.getId());
        return ResponseEntity.created(uri).body(comment);
    }

    @GetMapping("/post/{post_id}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable(name = "post_id") Long postId){
        List<CommentDTO> list = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/post/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDTO> getCommentById(
            @PathVariable(name = "post_id") Long postId,
            @PathVariable(name = "comment_id") Long commentId
    ) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/post/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable(name = "post_id") Long postId,
            @PathVariable(name = "comment_id") Long commentId,
            @Valid @RequestBody CommentDTO commentDTO
    ) {
        return ResponseEntity.ok(commentService.updateCommentById(postId, commentId, commentDTO));
    }

    @DeleteMapping("/post/{post_id}/comments/{comment_id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable(name = "post_id") Long postId,
            @PathVariable(name = "comment_id") Long commentId
    ) {
        commentService.deleteCommentById(postId, commentId);
        return ResponseEntity.noContent().build();
    }
}
