package com.example.journalapp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Setter;
import lombok.Getter;
@Getter
@Setter
@Document(collection = "app_settings")
public class AppSetting {
    @Id
    private ObjectId id;
    private String key;
    private String value;
}