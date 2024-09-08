package com.example.firstProject.init;

import com.example.firstProject.models.User;
import com.example.firstProject.repositories.UserRespository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminInitializer {

    @Autowired
    private UserRespository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.password}")
    private String ADMIN_PASSWORD;

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
