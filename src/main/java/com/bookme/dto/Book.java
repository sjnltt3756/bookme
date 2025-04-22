package com.bookme.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Book {
    private String title;
    private List<String> authors;
    private String thumbnail;
    private String infoLink;

    public Book(String title, List<String> authors, String thumbnail, String infoLink) {
        this.title = title;
        this.authors = authors;
        this.thumbnail = thumbnail;
        this.infoLink = infoLink;
    }
}
