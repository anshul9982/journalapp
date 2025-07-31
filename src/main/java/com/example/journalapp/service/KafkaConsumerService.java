package com.example.journalapp.service;

import com.example.journalapp.entity.JournalEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "journal-entry-events", groupId = "journal-app-group")
    public void consume(JournalEntry journalEntry) {
        logger.info(String.format("Consumed journal entry event <- %s", journalEntry.getId()));
        // TODO: Add logic here to process the consumed message.
        // For example: trigger sentiment analysis, send notifications, update analytics, etc.
    }
}