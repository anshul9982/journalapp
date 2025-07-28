package com.example.journalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.journalapp.entity.Quotes;
@Service
public class QuoteService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;

    public Quotes getRandomQuote(){
        String url = appCache.getKey("zenQuoteApiUrl");
        Quotes[] quotes = restTemplate.getForObject(url, Quotes[].class);
        return quotes != null? quotes[0] : null;
    }


}
