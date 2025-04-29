package com.hgb7725.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
        description = "PostDTO Model Information"
)
public class PostDTO {

    private Long id;

    @Schema(
            description = "Blog Post Title"
    )
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Schema(
            description = "Blog Post Description"
    )
    @Size(min = 10, message = "Post title should have at least 10 characters")
    private String description;

    @NotBlank(message = "Content must be not BLANK")
    private String content;

    @Schema(
            description = "Blog Post Category"
    )
    @NotNull
    private Long categoryId;

}
