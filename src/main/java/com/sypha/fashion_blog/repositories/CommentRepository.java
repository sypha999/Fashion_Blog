package com.sypha.fashion_blog.repositories;

import com.sypha.fashion_blog.models.Comments;
import com.sypha.fashion_blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments,Long> {

    List <Comments> findByPost(Post post);
}
