package com.example.journalapp.service;

import com.example.journalapp.entity.User;
import com.example.journalapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceImplTest {
    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    @Mock
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // The @ExtendWith(MockitoExtension.class) handles mock initialization.
        // This setup method is now used for creating test data.
        testUser = User.builder()
                       .userName("Sashank")
                       .password("password")
                       .roles(Arrays.asList("USER"))
                       .build();
    }

    @Test
    void loadUserByUsername_whenUserExists() {
        when(userRepository.findByUserName("Sashank")).thenReturn(testUser);
        UserDetails userDetails = userDetailService.loadUserByUsername("Sashank");
        assertNotNull(userDetails);
        assertEquals("Sashank", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_whenUserDoesNotExist_throwsException() {
        when(userRepository.findByUserName("nonexistent")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername("nonexistent"));
    }
}
