package com.sypha.fashion_blog.services;

import com.sypha.fashion_blog.dtos.CommentDto;
import com.sypha.fashion_blog.dtos.LoginDto;
import com.sypha.fashion_blog.dtos.PostResponseDto;
import com.sypha.fashion_blog.dtos.SignUpDto;


public interface CustomerServices {
    void register(SignUpDto signUpDto);
    void login(LoginDto loginDto);
    void likePost(Long post_id, Long customer_id);
    PostResponseDto viewPost(Long id);
    void comment(CommentDto commentDto, Long post_id, Long customer_id);
    void logout();

}
