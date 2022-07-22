package com.sypha.fashion_blog.controllers;

import com.sypha.fashion_blog.dtos.CommentDto;
import com.sypha.fashion_blog.dtos.LoginDto;
import com.sypha.fashion_blog.dtos.PostResponseDto;
import com.sypha.fashion_blog.dtos.SignUpDto;
import com.sypha.fashion_blog.enums.Role;
import com.sypha.fashion_blog.repositories.CommentRepository;
import com.sypha.fashion_blog.repositories.CustomerRepository;
import com.sypha.fashion_blog.repositories.FavouriteRepository;
import com.sypha.fashion_blog.repositories.PostRepository;
import com.sypha.fashion_blog.services.CustomerServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    FavouriteRepository favouriteRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    HttpSession httpSession;
    Role role;






    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        CustomerServiceImple customer=new CustomerServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, role, httpSession);
        customer.login(loginDto);
        return new ResponseEntity<>("login successful", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        CustomerServiceImple customer=new CustomerServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, role, httpSession);
        customer.logout();
        return new ResponseEntity<>("logout successful", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUpDto signUpDto){
        CustomerServiceImple customer=new CustomerServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, role, httpSession);
        customer.register(signUpDto);
        return new ResponseEntity<>("registration successful", HttpStatus.OK);
    }

    @PostMapping("/{customer_id}/{post_id}/like")
    public ResponseEntity<String> like(@PathVariable Long customer_id,@PathVariable Long post_id){
        CustomerServiceImple customer=new CustomerServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, role, httpSession);
        customer.likePost(customer_id,post_id);
        return new ResponseEntity<>("action perfomed", HttpStatus.OK);
    }

    @PostMapping("/{post_id}/view_post")
    public PostResponseDto view(@PathVariable Long post_id){
        CustomerServiceImple customer=new CustomerServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, role, httpSession);
        return customer.viewPost(post_id);
    }

    @PostMapping("/{post_id}/{customer_id}/comment")
    public ResponseEntity<String> comment(@RequestBody CommentDto commentDto, @PathVariable Long post_id, @PathVariable Long customer_id){
        CustomerServiceImple customer=new CustomerServiceImple(postRepository,customerRepository,favouriteRepository,commentRepository, role, httpSession);
        customer.comment(commentDto,post_id,customer_id);
        return new ResponseEntity<>("comment uploaded",HttpStatus.OK);
    }

}
