package com.example.journalapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.journalapp.entity.JournalEntry;
import com.example.journalapp.entity.Quotes;
import com.example.journalapp.entity.User;
import com.example.journalapp.repository.JournalEntryRepository;
@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private QuoteService quoteService;
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUsername(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            log.error("exception :", e);
            throw new RuntimeException("an error occured suring saving entry", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){return journalEntryRepository.findById(id);}

    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userService.findByUsername(userName);
            if (user != null) {
                removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
                if (removed) {
                    userService.saveUser(user);
                    journalEntryRepository.deleteById(id);
                }
            }
        } catch (Exception e) {
            log.error("Error deleting journal entry {} for user {}:", id, userName, e);
        }
        return removed;
    }

    public List<JournalEntry> getAllEntriesForAuthenticatedUser() {
        String userName = getAuthenticatedUsername();
        User user = userService.findByUsername(userName);
        return user.getJournalEntries();
    }

    public JournalEntry createEntryForAuthenticatedUser(JournalEntry journalEntry) {
        String userName = getAuthenticatedUsername();
        saveEntry(journalEntry, userName);
        return journalEntry;
    }

    public JournalEntry getEntryByIdForAuthenticatedUser(ObjectId id) {
        String userName = getAuthenticatedUsername();
        User user = userService.findByUsername(userName);
        return user.getJournalEntries().stream()
                .filter(entry -> entry.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Entry not found"));
    }

    public void deleteEntryByIdForAuthenticatedUser(ObjectId id) {
        String userName = getAuthenticatedUsername();
        if (!deleteById(id, userName)) {
            throw new RuntimeException("Entry not found or could not be deleted");
        }
    }

    public JournalEntry updateEntryForAuthenticatedUser(ObjectId id, JournalEntry newEntry) {
        String userName = getAuthenticatedUsername();
        User user = userService.findByUsername(userName);
        JournalEntry oldEntry = user.getJournalEntries().stream()
                .filter(entry -> entry.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
        oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
        saveEntry(oldEntry);
        return oldEntry;
    }

    public Quotes getRandomQuote() {
        try {
            return quoteService.getRandomQuote();
        } catch (Exception e) {
            log.error("Error fetching random quote", e);
            throw new RuntimeException("Could not fetch a random quote", e);
        }
    }

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
