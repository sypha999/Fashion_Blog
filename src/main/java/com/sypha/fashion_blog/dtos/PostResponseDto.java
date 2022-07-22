package com.sypha.fashion_blog.dtos;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PostResponseDto {
    private String title;
    private String description;
    private LocalDateTime date;
    private String category;
    private Long numOfLikes;
}
