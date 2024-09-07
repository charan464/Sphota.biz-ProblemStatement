package com.example.firstProject.config;

import com.example.firstProject.models.User;
import com.example.firstProject.repositories.UserRespository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminInitializer{

    @Autowired
    private UserRespository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    public void run(){
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("SaiCharan@8688011464"));
            adminUser.setRoles(Set.of("ADMIN"));
            userRepository.save(adminUser);
        }
    }
}
