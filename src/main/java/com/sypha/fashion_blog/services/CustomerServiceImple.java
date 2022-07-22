package com.sypha.fashion_blog.services;

import com.sypha.fashion_blog.dtos.CommentDto;
import com.sypha.fashion_blog.dtos.LoginDto;
import com.sypha.fashion_blog.dtos.PostResponseDto;
import com.sypha.fashion_blog.dtos.SignUpDto;
import com.sypha.fashion_blog.enums.Role;
import com.sypha.fashion_blog.exceptions.CustomAppException;
import com.sypha.fashion_blog.models.Comments;
import com.sypha.fashion_blog.models.Customer;
import com.sypha.fashion_blog.models.Favourites;
import com.sypha.fashion_blog.models.Post;
import com.sypha.fashion_blog.repositories.CommentRepository;
import com.sypha.fashion_blog.repositories.CustomerRepository;
import com.sypha.fashion_blog.repositories.FavouriteRepository;
import com.sypha.fashion_blog.repositories.PostRepository;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

public class CustomerServiceImple implements CustomerServices{

    final PostRepository postRepository;
    final CustomerRepository customerRepository;
    final FavouriteRepository favouriteRepository;
    final CommentRepository commentRepository;
    final Role role;
    final HttpSession httpSession;

    public CustomerServiceImple(PostRepository postRepository, CustomerRepository customerRepository, FavouriteRepository favouriteRepository, CommentRepository commentRepository, Role role, HttpSession httpSession) {
        this.postRepository = postRepository;
        this.customerRepository = customerRepository;
        this.favouriteRepository = favouriteRepository;
        this.commentRepository = commentRepository;
        this.role = role;

        this.httpSession = httpSession;
    }

    @Override
    public void register(SignUpDto signUpDto) {
        Optional cusInDB = customerRepository.findByEmail(signUpDto.getEmail());
        if(cusInDB.isPresent()){throw new CustomAppException("Email"+signUpDto.getEmail()+"is already in use", HttpStatus.CONFLICT);}
        Customer customer=new Customer();
        customer.setFullName(signUpDto.getFullName());
        customer.setPassword(signUpDto.getPassword());
        customer.setEmail(signUpDto.getEmail());
        customer.setRole(role.USER);
        customerRepository.save(customer);
    }

    @Override
    public void login(LoginDto loginDto) {
        Customer cusExist=customerRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        if(cusExist.getId()<0){ throw new CustomAppException("Wrong email or password",HttpStatus.UNAUTHORIZED);}
        httpSession.setAttribute("userEmail",cusExist.getEmail());
        httpSession.setAttribute("userRole",cusExist.getRole());
        httpSession.setAttribute("userFullName",cusExist.getFullName());
        httpSession.setAttribute("userId",cusExist.getId());
    }

    @Override
    public void likePost(Long post_id, Long customer_id) {
        Post post = postRepository.findById(post_id).orElseThrow(()-> new CustomAppException("post not found",HttpStatus.NOT_FOUND));
        Customer customer = customerRepository.findById(customer_id).orElseThrow();
        System.out.println(post.toString());
        Optional <Favourites> favInDb=favouriteRepository.findByCustomer_idAndPost_id(customer.getId(),post.getId());
        if(favInDb.isEmpty()){
        Favourites fav=new Favourites();
        fav.setCustomer(customer);
        fav.setPost(post);
        favouriteRepository.save(fav);
        post.setNumOfLikes(post.getNumOfLikes() + 1);}
        if(favInDb.isPresent()){
            favouriteRepository.delete(favInDb.get());
            post.setNumOfLikes(post.getNumOfLikes() - 1);
        }
    }

    @Override
    public PostResponseDto viewPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new CustomAppException("post not found",HttpStatus.NOT_FOUND));
        PostResponseDto postDto = new PostResponseDto();
        postDto.setDate(post.getDate());
        postDto.setDescription(post.getDescription());
        postDto.setTitle(post.getTitle());
        postDto.setNumOfLikes(post.getNumOfLikes());
        return postDto;
    }

    @Override
    public void comment(CommentDto commentDto,Long post_id, Long customer_id) {
        Customer customer=customerRepository.findById(customer_id).orElseThrow(()-> new CustomAppException("customer not found",HttpStatus.NOT_FOUND));
        Post post=postRepository.findById(post_id).orElseThrow(()-> new CustomAppException("post not found",HttpStatus.NOT_FOUND));
        Comments comments=new Comments();
        comments.setComment(commentDto.getComment());
        comments.setTimeOfComment(LocalDateTime.now());
        comments.setCustomer(customer);
        comments.setPost(post);
        commentRepository.save(comments);
    }

    @Override
    public void logout() {
       httpSession.invalidate();
    }
}
