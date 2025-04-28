package com.hgb7725.blog.service.impl;

import com.hgb7725.blog.entity.Category;
import com.hgb7725.blog.entity.Post;
import com.hgb7725.blog.exception.BlogAPIException;
import com.hgb7725.blog.exception.ResourceNotFoundException;
import com.hgb7725.blog.payload.PostDTO;
import com.hgb7725.blog.payload.PostResponse;
import com.hgb7725.blog.repository.CategoryRepository;
import com.hgb7725.blog.repository.PostRepository;
import com.hgb7725.blog.service.PostService;
import com.hgb7725.blog.utils.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        if(postDTO.getId() != null) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Resource ID must not be provided for creation");
        }
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getCategoryId()));
        Post post = mapper.map(postDTO, Post.class);
        post.setCategory(category);
        return mapper.map(postRepository.save(post), PostDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                            ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort) ;
        Page<Post> pagePost = postRepository.findAll(pageable);
        List<Post> list = pagePost.toList();
        List<PostDTO> listPostDTO = list.stream().map(post -> mapper.map(post, PostDTO.class)).toList();
        return new PostResponse(
                listPostDTO, pageNo, pageSize, pagePost.getTotalElements(), pagePost.getTotalPages(), pagePost.isLast()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PostDTO getPostByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_POST, Constants.FIELD_ID, postId));
        return mapper.map(post, PostDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDTO> getPostsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_CATEGORY, Constants.FIELD_ID, categoryId));
        Set<Post> setPosts = category.getPosts();
        return setPosts.stream().map(element -> mapper.map(element, PostDTO.class)).toList();
    }

    @Transactional
    @Override
    public PostDTO updatePost(Long postId, PostDTO postDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_POST, "id", postId));
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_CATEGORY, Constants.FIELD_ID, postDTO.getCategoryId()));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());
        post.setCategory(category);
        return mapper.map(post, PostDTO.class);
    }


    @Transactional
    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_POST, Constants.FIELD_ID, postId));
        postRepository.delete(post);
    }
}
