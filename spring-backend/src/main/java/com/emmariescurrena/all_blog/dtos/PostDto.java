package com.emmariescurrena.all_blog.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
    
    @NotEmpty(message = "The title is required")
    @Size(min = 2, max = 256, message = "The length of title must be between 2 and 256 characters")
    private final String title;
    
    @NotEmpty(message = "The body is required")
    @Size(min = 50, max = 20000, message = "The length of body must be between 50 and 20000 characters")
    private final String body;

}
