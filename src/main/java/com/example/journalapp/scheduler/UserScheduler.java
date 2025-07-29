package com.example.journalapp.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.journalapp.entity.JournalEntry;
import java.util.stream.Collectors;
import com.example.journalapp.entity.User;
import com.example.journalapp.repository.UserRepositoryImpl;
import com.example.journalapp.service.AppCache;
import com.example.journalapp.service.EmailService;
import com.example.journalapp.service.SentimentAnalysisService;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendEmail() {

        List<User> users = userRepositoryImpl.getUsersForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(1))).map(x -> x.getContent())
                    .collect(Collectors.toList());

            String entry = String.join("\n", filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendSimpleMessage(user.getEmail(), "Sentiment for last 7 days", sentiment);
        }
    }
    @Scheduled(cron = "0 0/10 * * * ?")
    public void clearAppCache(){
        appCache.init();
    }
}
