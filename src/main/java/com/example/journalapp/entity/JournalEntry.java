package com.example.journalapp.entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.journalapp.Enum.Sentiment;
import com.mongodb.lang.NonNull;
@Data
@NoArgsConstructor

@Document(collection="journal_entries")
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private Sentiment sentiment;
    private String content;
    private LocalDateTime date;
}
