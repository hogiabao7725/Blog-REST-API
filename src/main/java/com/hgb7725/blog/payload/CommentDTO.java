package com.hgb7725.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;

    @NotEmpty(message = "Name should not be Empty")
    private String name;

    @NotEmpty(message = "Email should not be Empty")
    @Email(message = "Email is not Validation")
    private String email;

    @NotEmpty(message = "Body should not be Empty")
    @Size(min = 10, message = "Body should have at least 10 characters")
    private String body;
}
