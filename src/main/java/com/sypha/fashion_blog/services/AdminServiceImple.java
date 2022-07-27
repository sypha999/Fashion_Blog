package com.sypha.fashion_blog.services;

import com.sypha.fashion_blog.dtos.*;
import com.sypha.fashion_blog.enums.Category;
import com.sypha.fashion_blog.enums.Role;
import com.sypha.fashion_blog.exceptions.CustomAppException;
import com.sypha.fashion_blog.models.Comments;
import com.sypha.fashion_blog.models.Customer;
import com.sypha.fashion_blog.models.Post;
import com.sypha.fashion_blog.repositories.CommentRepository;
import com.sypha.fashion_blog.repositories.CustomerRepository;
import com.sypha.fashion_blog.repositories.FavouriteRepository;
import com.sypha.fashion_blog.repositories.PostRepository;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AdminServiceImple implements AdminServices{

    final PostRepository postRepository;
    final CustomerRepository customerRepository;
    final FavouriteRepository favouriteRepository;
    final CommentRepository commentRepository;

    final HttpSession httpSession;

    final Role role;

    public AdminServiceImple(PostRepository postRepository, CustomerRepository customerRepository, FavouriteRepository favouriteRepository, CommentRepository commentRepository, HttpSession httpSession, Role role) {
        this.postRepository = postRepository;
        this.customerRepository = customerRepository;
        this.favouriteRepository = favouriteRepository;
        this.commentRepository = commentRepository;
        this.httpSession = httpSession;
        this.role = role;
    }

    @Override
    public void createPost(PostDto postDto) {
        if(httpSession.getAttribute("adminRole")!=role.ADMIN){
            throw new CustomAppException("You are unauthorized to perform this action",HttpStatus.FORBIDDEN);
        }
        Post post =new Post();
        post.setCategory(Category.valueOf(postDto.getCategory()));
        post.setDate(LocalDateTime.now());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        postRepository.save(post);
    }

    @Override
    public LikedPostDto viewLiked(Long post_id) {
        if(httpSession.getAttribute("adminRole")!=role.ADMIN){
            throw new CustomAppException("You are unauthorized to perform this action",HttpStatus.FORBIDDEN);
        }
        Post favs=postRepository.findById(post_id).orElseThrow(()-> new CustomAppException("post not found", HttpStatus.NOT_FOUND));
        LikedPostDto fav = new LikedPostDto();
        fav.setNumOfLikes(favs.getNumOfLikes());
        fav.setTitle(favs.getTitle());
        fav.setDescription(favs.getDescription());
        return fav;
    }

    @Override
    public List<CommentDto> viewComments(Long post_id) {
        List<CommentDto>all=new ArrayList<>();
        Post post = postRepository.findById(post_id).orElseThrow();
        List<Comments> comments =commentRepository.findByPost(post);
        for(Comments i:comments){
            CommentDto comm = new CommentDto();
            comm.setComment(i.getComment());
            all.add(comm);}
        return all;
    }

    @Override
    public void logout() {
        httpSession.invalidate();
    }

    @Override
    public void register(SignUpDto signUpDto) {
        Optional<Customer> cusInDB = customerRepository.findByEmail(signUpDto.getEmail());
        if(cusInDB.isPresent()){throw new CustomAppException("Email "+signUpDto.getEmail()+" is already in use", HttpStatus.CONFLICT);}
        Customer customer=new Customer();
        customer.setFullName(signUpDto.getFullName());
        customer.setPassword(signUpDto.getPassword());
        customer.setEmail(signUpDto.getEmail());
        customer.setRole(role.ADMIN);
        customerRepository.save(customer);
    }

    @Override
    public void login(LoginDto loginDto) {
        Customer cusExist=customerRepository.findByEmailAndPasswordAndRole(loginDto.getEmail(), loginDto.getPassword(),role.ADMIN);
        if(cusExist.getId()<0){ throw new CustomAppException("Wrong email or password",HttpStatus.UNAUTHORIZED);}

        httpSession.setAttribute("adminEmail",cusExist.getEmail());
        httpSession.setAttribute("adminRole",cusExist.getRole());
        httpSession.setAttribute("adminFullName",cusExist.getFullName());
        httpSession.setAttribute("adminId",cusExist.getId());

    }
}
