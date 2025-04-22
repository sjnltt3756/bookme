package com.bookme.controller;

import com.bookme.dto.Book;
import com.bookme.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/recommend")
    public String recommend(@RequestParam(value = "query", required = false) String query,
                            @RequestParam(value = "category", defaultValue = "title") String category,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "16") int size,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {

        int startIndex = page * size;
        List<Book> books = recommendService.getRecommendations(userDetails, query, category, startIndex, size);

        model.addAttribute("books", books);
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        model.addAttribute("category", category);
        model.addAttribute("size", size);
        return "recommend";
    }
}