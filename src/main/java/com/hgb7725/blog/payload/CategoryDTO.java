package com.hgb7725.blog.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "Name Category is not BLANK")
    private String name;

    @NotBlank(message = "Description Category is not BLANK")
    private String description;
}
