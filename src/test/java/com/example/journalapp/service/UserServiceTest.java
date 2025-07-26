package com.example.journalapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.journalapp.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindByUsername(){
        assertNotNull(userRepository.findByUserName("Anshul"));
    }

}
