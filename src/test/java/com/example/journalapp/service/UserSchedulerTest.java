package com.example.journalapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.journalapp.scheduler.UserScheduler;

@SpringBootTest
public class UserSchedulerTest {
    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void testFetchUserAndSendEmail() {
        userScheduler.fetchUserAndSendEmail();
    }

}
