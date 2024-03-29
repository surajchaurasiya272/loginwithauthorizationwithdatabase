package com.example.olginregister.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.olginregister.model.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User,String> {

    public User  findByUsername(String username);

    
} 
