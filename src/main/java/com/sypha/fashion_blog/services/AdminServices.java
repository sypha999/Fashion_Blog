package com.sypha.fashion_blog.services;

import com.sypha.fashion_blog.dtos.*;

import java.util.List;

public interface AdminServices {
    void createPost(PostDto postDto);
    LikedPostDto viewLiked(Long post_id);
    List<CommentDto> viewComments(Long post_id);
    void logout();

    void register (SignUpDto signUpDto);

    void login(LoginDto loginDto);
}
