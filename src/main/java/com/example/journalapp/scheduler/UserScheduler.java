package com.example.journalapp.scheduler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.journalapp.Enum.Sentiment;
import com.example.journalapp.entity.JournalEntry;
import java.util.stream.Collectors;
import com.example.journalapp.entity.User;
import com.example.journalapp.repository.UserRepositoryImpl;
import com.example.journalapp.service.AppCache;
import com.example.journalapp.service.EmailService;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @Autowired
    private AppCache appCache;

    //@Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendEmail() {

        List<User> users = userRepositoryImpl.getUsersForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(entry -> entry.getDate().isAfter(LocalDateTime.now().minusDays(7)))
                    .map(JournalEntry::getSentiment)
                    .collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            
            for (Sentiment sentiment : sentiments) {
                if (sentiment!=null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                emailService.sendSimpleMessage(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentiment.toString());
            }
        }
    }
    @Scheduled(cron = "0 0/10 * * * ?")
    public void clearAppCache(){
        appCache.init();
    }
}
