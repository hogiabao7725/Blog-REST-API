package com.hgb7725.blog.controller;

import com.hgb7725.blog.payload.PostDTO;
import com.hgb7725.blog.payload.PostResponse;
import com.hgb7725.blog.service.PostService;
import com.hgb7725.blog.utils.BuildURIForController;
import com.hgb7725.blog.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST APIs for Post Resource"
)
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create Post",
            description = "Create a new blog post. Only accessible by users with ADMIN role."
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
            // NOTE: Should write error description
    )
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        PostDTO post = postService.createPost(postDTO);
        URI uri = BuildURIForController.buildURIForController(post.getId());
        return ResponseEntity.created(uri).body(post);
    }

    @GetMapping
    @Operation(
            summary = "Get All Posts",
            description = "Retrieve a paginated and sorted list of all blog posts. Supports pagination and sorting parameters."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
            // NOTE: Should write error description
    )
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(name = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{postId}")
    @Operation(
            summary = "Get Post By PostId",
            description = "Retrieve details of a specific blog post using its ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
            // NOTE: Should write error description
    )
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostByPostId(postId));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(
            summary = "Get Post list By CategoryId",
            description = "Retrieve all blog posts associated with a specific category by category ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
            // NOTE: Should write error description
    )
    public ResponseEntity<List<PostDTO>> getPostsByCategoryId(@PathVariable Long categoryId) {
        List<PostDTO> list = postService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update Post By PostId",
            description = "Update an existing blog post's details using its ID. Only accessible by users with ADMIN role."
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
            // NOTE: Should write error description
    )
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long postId,
                                              @Valid @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.updatePost(postId, postDTO));
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete Post by PostId",
            description = "Delete a blog post permanently by its ID. Only accessible by users with ADMIN role."
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @ApiResponse(
            responseCode = "204",
            description = "HTTP Status 204 No Content"
            // NOTE: Should write error description
    )
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
