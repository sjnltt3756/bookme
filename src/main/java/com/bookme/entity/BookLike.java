package com.bookme.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class BookLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String thumbnail;
    private String infoLink;

    @ElementCollection
    private List<String> authors;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
