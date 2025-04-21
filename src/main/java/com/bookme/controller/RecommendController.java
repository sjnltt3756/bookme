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
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        List<Book> books = recommendService.getRecommendations(userDetails, query);
        model.addAttribute("books", books);
        return "recommend";
    }
}