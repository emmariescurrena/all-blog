package com.emmariescurrena.all_blog.dtos;

import lombok.Data;

@Data
public class CreatePostDto {
    private String title;
    
    private String body;

    private Long userId;
}
