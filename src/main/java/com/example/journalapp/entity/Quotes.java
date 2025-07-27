package com.example.journalapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quotes {
    @JsonProperty("q")
    private String quote;
    @JsonProperty("a")
    private String author;
}
