package com.example.journalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.journalapp.entity.Quotes;
@Service
public class QuoteService {
    @Autowired
    private RestTemplate restTemplate;
    private final String url = "https://zenquotes.io/api/random";

    public Quotes getRandomQuotes(){
        Quotes[] quotes = restTemplate.getForObject(url, Quotes[].class);
        return quotes != null? quotes[0] : null;
    }


}
