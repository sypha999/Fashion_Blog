package com.sypha.fashion_blog.controllers;

import com.sypha.fashion_blog.dtos.*;
import com.sypha.fashion_blog.enums.Role;
import com.sypha.fashion_blog.repositories.CommentRepository;
import com.sypha.fashion_blog.repositories.CustomerRepository;
import com.sypha.fashion_blog.repositories.FavouriteRepository;
import com.sypha.fashion_blog.repositories.PostRepository;
import com.sypha.fashion_blog.services.AdminServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    FavouriteRepository favouriteRepository;
    @Autowired
    PostRepository postRepository;

    Role role;
    @Autowired
    HttpSession httpSession;



    @PostMapping("/admin/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        AdminServiceImple admin=new AdminServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, httpSession, role);
        admin.login(loginDto);
        return new ResponseEntity<>("login successful", HttpStatus.OK);
    }

    @PostMapping("/admin/logout")
    public ResponseEntity<String> logout(){
        AdminServiceImple admin=new AdminServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, httpSession, role);
        admin.logout();
        return new ResponseEntity<>("logout successful", HttpStatus.OK);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<String> register(@RequestBody SignUpDto signUpDto){
        AdminServiceImple admin=new AdminServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, httpSession, role);
        admin.register(signUpDto);
        return new ResponseEntity<>("registration successful", HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestBody PostDto postDto){
        AdminServiceImple admin=new AdminServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, httpSession, role);
        admin.createPost(postDto);
        return new ResponseEntity<>("post created successfully", HttpStatus.OK);
    }

    @PostMapping("/{post_id}/view")
    public LikedPostDto viewLiked(@PathVariable Long post_id){
        AdminServiceImple admin=new AdminServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, httpSession, role);
        return admin.viewLiked(post_id);
    }

    @PostMapping("/{post_id}/comments")
    public List<CommentDto> viewComments(@PathVariable Long post_id){
        AdminServiceImple admin=new AdminServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, httpSession, role);
        return admin.viewComments(post_id);
    }
}
