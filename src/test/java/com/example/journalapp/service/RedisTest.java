package com.example.journalapp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void testSendMail(){
        redisTemplate.opsForValue().set("email", "something@email.com");
        Object name = redisTemplate.opsForValue().get("name");
        int a = 1;

    }

}
