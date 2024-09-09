package com.example.firstProject.init;

import com.example.firstProject.models.User;
import com.example.firstProject.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.password}")
    private String ADMIN_PASSWORD;

    // adds a row for admin in users table if the row doesn't exist
    // doing it allows us to use the same login logic for admin and normal user
    @PostConstruct
    public void run() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            adminUser.setRoles(Set.of("ADMIN"));
            userRepository.save(adminUser);
        }
    }
}
