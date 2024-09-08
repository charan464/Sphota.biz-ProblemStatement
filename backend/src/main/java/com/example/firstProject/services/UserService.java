package com.example.firstProject.services;

import com.example.firstProject.exception.UserAlreadyExistsException;
import com.example.firstProject.models.User;
import com.example.firstProject.repositories.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    UserRespository userRespository;


    public void saveUser(User user) throws UserAlreadyExistsException {
        Optional<User> existingUser = userRespository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(Set.of("USER"));
        userRespository.save(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRespository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }
}
