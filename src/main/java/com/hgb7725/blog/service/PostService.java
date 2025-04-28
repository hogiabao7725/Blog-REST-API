package com.hgb7725.blog.service;

import com.hgb7725.blog.payload.PostDTO;
import com.hgb7725.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO getPostByPostId(Long postId);

    List<PostDTO>  getPostsByCategoryId(Long categoryId);

    PostDTO updatePost(Long postId, PostDTO postDTO);

    void deletePost(Long postId);
}
