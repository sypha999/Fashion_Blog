package com.sypha.fashion_blog.dtos;

import lombok.Data;

@Data
public class SignUpDto {
    private String fullName;
    private String email;
    private String password;
}
