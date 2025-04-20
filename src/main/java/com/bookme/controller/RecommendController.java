package com.bookme.controller;

import com.bookme.dto.Book;
import com.bookme.service.GoogleBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecommendController {

    @Autowired
    private GoogleBooksService googleBooksService;

    @GetMapping("/recommend")
    public String recommend(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query != null && !query.isBlank()) {
            List<Book> books = googleBooksService.searchBooks(query);
            model.addAttribute("books", books);
        } else {
            model.addAttribute("books", new ArrayList<>()); // 초기에는 빈 리스트
        }
        return "recommend";
    }
}
