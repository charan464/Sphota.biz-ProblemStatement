package com.example.firstProject.service;

import com.example.firstProject.exception.UserAlreadyExistsException;
import com.example.firstProject.models.User;
import com.example.firstProject.repositories.UserRepository;
import com.example.firstProject.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser_Success() throws UserAlreadyExistsException {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());
        userService.addUser(user);
        assertEquals(Set.of("USER"), user.getRoles());
    }

    @Test
    void testAddUser_UserAlreadyExists() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.addUser(user);
        });
    }

    @Test
    void testLoadUserByUsername_Found() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        user.setRoles(Set.of("USER"));
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("testUser");
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("testUser");
        });
    }
}

