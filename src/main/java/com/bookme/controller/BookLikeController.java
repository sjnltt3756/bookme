package com.bookme.controller;

import com.bookme.entity.User;
import com.bookme.entity.enums.LikeResult;
import com.bookme.repository.UserRepository;
import com.bookme.service.BookLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookLikeController {

    private final UserRepository userRepository;
    private final BookLikeService bookLikeService;

    @PostMapping("/like")
    public String likeBook(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam String title,
                           @RequestParam String thumbnail,
                           @RequestParam String infoLink,
                           @RequestParam String authors,
                           @RequestParam(required = false, defaultValue = "toggle") String mode,
                           RedirectAttributes redirect) {

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        LikeResult result;

        switch (mode) {
            case "like" -> result = bookLikeService.likeOnly(user, title, thumbnail, infoLink, List.of(authors.split(",")));
            case "unlike" -> result = bookLikeService.unlikeOnly(user, title);
            default -> result = bookLikeService.toggleLike(user, title); // 기본은 toggle
        }

        String message = switch (result) {
            case LIKED -> "찜 완료!";
            case UNLIKED -> "찜 취소됨!";
            case ALREADY_LIKED -> "이미 찜한 책입니다.";
            case NOT_LIKED -> "찜되지 않은 책입니다.";
        };

        redirect.addFlashAttribute("likeMessage", message);
        return "redirect:/recommend";
    }

    @GetMapping("/wishlist")
    public String wishlist(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("likedBooks", bookLikeService.getLikedBooks(user));
        return "wishlist";
    }
}
