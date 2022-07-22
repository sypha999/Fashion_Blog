package com.sypha.fashion_blog.repositories;

import com.sypha.fashion_blog.models.Customer;
import com.sypha.fashion_blog.models.Favourites;
import com.sypha.fashion_blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FavouriteRepository extends JpaRepository<Favourites,Long> {

       Optional <Favourites> findByCustomer_idAndPost_id(Long customer_id, Long post_id);

}
