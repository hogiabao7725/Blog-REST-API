package com.hgb7725.blog.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private Long id;

    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Size(min = 10, message = "Post title should have at least 10 characters")
    private String description;

    @NotBlank(message = "Content must be not BLANK")
    private String content;

    @NotNull
    private Long categoryId;

}
