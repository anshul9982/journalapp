package com.example.journalapp.repository;

import com.example.journalapp.entity.AppSetting;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppSettingRepository extends MongoRepository<AppSetting, ObjectId> {
}