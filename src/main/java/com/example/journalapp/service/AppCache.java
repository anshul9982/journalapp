package com.example.journalapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.journalapp.entity.AppSetting;
import com.example.journalapp.repository.AppSettingRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

    @Autowired
    private AppSettingRepository appSettingRepository;

    private Map<String, String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<AppSetting> allSettings = appSettingRepository.findAll();
        for (AppSetting setting : allSettings) {
            appCache.put(setting.getKey(), setting.getValue());
        }
    }

    public String getKey(String key){
        return appCache.get(key);
    }


}
