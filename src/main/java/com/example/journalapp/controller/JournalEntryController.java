package com.example.journalapp.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalapp.entity.JournalEntry;
import com.example.journalapp.entity.Quotes;
import com.example.journalapp.service.JournalEntryService;
@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    JournalEntryService journalEntryService;

    @GetMapping()
    public ResponseEntity<?> getAllEntriesOfUser() {
        try {
            List<JournalEntry> entries = journalEntryService.getAllEntriesForAuthenticatedUser();
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching entries", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            JournalEntry createdEntry = journalEntryService.createEntryForAuthenticatedUser(myEntry);
            return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating entry", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/quote")
    public ResponseEntity<?> getQuote() {
        try {
            Quotes quote = journalEntryService.getRandomQuote();
            return new ResponseEntity<>(quote, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching quote", e);
            return new ResponseEntity<>("COULD NOT FETCH A QUOTE", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myId) {
        try {
            JournalEntry entry = journalEntryService.getEntryByIdForAuthenticatedUser(myId);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching entry by ID", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId) {
        try {
            journalEntryService.deleteEntryByIdForAuthenticatedUser(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting entry", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        try {
            JournalEntry updatedEntry = journalEntryService.updateEntryForAuthenticatedUser(id, newEntry);
            return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating entry", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
