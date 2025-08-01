package com.example.journalapp.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.journalapp.Enum.Sentiment;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String userName;

    private String email;
    private boolean sentimentAnalysis;

    @NonNull
    private String password;

    @DBRef
    @Builder.Default
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles;

}