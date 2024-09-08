package com.example.firstProject.repositories;

import com.example.firstProject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository interface for managing User entities
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
