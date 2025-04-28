package com.hgb7725.blog.service;

import com.hgb7725.blog.payload.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getCommentsByPostId(Long postId);

    CommentDTO getCommentById(Long postId, Long commentId);

    CommentDTO createComment(Long id, CommentDTO commentDTO);

    CommentDTO updateCommentById(Long postId, Long commentId, CommentDTO commentDTO);

    void deleteCommentById(Long postId, Long commentId);

}
