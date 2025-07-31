package com.example.journalapp.service;

import com.example.journalapp.entity.JournalEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "journal-entry-events";

    @Autowired
    private KafkaTemplate<String, JournalEntry> kafkaTemplate;

    public void sendMessage(JournalEntry journalEntry) {
        logger.info(String.format("Producing journal entry event -> %s", journalEntry.getId()));
        this.kafkaTemplate.send(TOPIC, journalEntry);
    }
}