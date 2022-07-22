package com.sypha.fashion_blog.repositories;

import com.sypha.fashion_blog.enums.Role;
import com.sypha.fashion_blog.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer>findByEmail(String email);
     Customer findByEmailAndPassword(String email, String password);

    Customer findByEmailAndPasswordAndRole(String email, String password, Role role);
}
