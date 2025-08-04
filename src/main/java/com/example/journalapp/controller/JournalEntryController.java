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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@Slf4j
@RestController
@RequestMapping("/journal")
@Tag(name = "Journal Entry Controller", description = "APIs for managing journal entries for the authenticated user.")
public class JournalEntryController {
    @Autowired
    JournalEntryService journalEntryService;

    @GetMapping()
    @Operation(summary = "Get all journal entries for the authenticated user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved list"), @ApiResponse(responseCode = "404", description = "No entries found") })
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
    @Operation(summary = "Create a new journal entry for the authenticated user")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Journal entry created successfully"), @ApiResponse(responseCode = "400", description = "Bad request") })
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
    @Operation(summary = "Get a random quote")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved a quote"), @ApiResponse(responseCode = "500", description = "Internal server error, could not fetch a quote") })
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
    @Operation(summary = "Get a specific journal entry by its ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved the entry"), @ApiResponse(responseCode = "404", description = "Entry not found or does not belong to the user") })
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
    @Operation(summary = "Delete a specific journal entry by its ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Entry deleted successfully"), @ApiResponse(responseCode = "404", description = "Entry not found or does not belong to the user") })
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
    @Operation(summary = "Update a specific journal entry by its ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Entry updated successfully"), @ApiResponse(responseCode = "404", description = "Entry not found or does not belong to the user") })
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
