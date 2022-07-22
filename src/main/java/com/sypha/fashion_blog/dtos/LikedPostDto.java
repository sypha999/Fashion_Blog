package com.sypha.fashion_blog.dtos;

import lombok.Data;

@Data
public class LikedPostDto {
    private String title;
    private String description;
    private Long numOfLikes;
}
