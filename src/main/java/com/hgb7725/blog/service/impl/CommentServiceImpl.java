package com.hgb7725.blog.service.impl;

import com.hgb7725.blog.entity.Comment;
import com.hgb7725.blog.entity.Post;
import com.hgb7725.blog.exception.BlogAPIException;
import com.hgb7725.blog.exception.ResourceNotFoundException;
import com.hgb7725.blog.payload.CommentDTO;
import com.hgb7725.blog.repository.CommentRepository;
import com.hgb7725.blog.repository.PostRepository;
import com.hgb7725.blog.service.CommentService;
import com.hgb7725.blog.utils.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository,
                              CommentRepository commentRepository,
                              ModelMapper mapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_POST, Constants.FIELD_ID, postId));
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        return comments.stream().map(comment -> mapper.map(comment, CommentDTO.class)).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Comment comment = getCommentByIdOrThrow(postId, commentId);
        return mapper.map(comment, CommentDTO.class);
    }

    @Transactional
    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_POST, Constants.FIELD_ID, postId));
        Comment comment = mapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return mapper.map(savedComment, CommentDTO.class);
    }

    @Transactional
    @Override
    public CommentDTO updateCommentById(Long postId, Long commentId, CommentDTO commentDTO) {
        Comment comment = getCommentByIdOrThrow(postId, commentId);
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        return mapper.map(comment, CommentDTO.class);
    }

    @Transactional
    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Comment comment = getCommentByIdOrThrow(postId, commentId);
        commentRepository.delete(comment);
    }

    private Comment getCommentByIdOrThrow(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_POST, Constants.FIELD_ID, postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_COMMENT, Constants.FIELD_ID, commentId));
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }
        return comment;
    }

}
