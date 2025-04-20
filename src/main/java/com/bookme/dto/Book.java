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

    public Book(String title, List<String> authors, String thumbnail) {
        this.title = title;
        this.authors = authors;
        this.thumbnail = thumbnail;
    }
}
