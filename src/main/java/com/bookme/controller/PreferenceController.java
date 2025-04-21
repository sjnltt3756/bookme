package com.bookme.controller;

import com.bookme.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @GetMapping("/preference")
    public String preferenceForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("groupedGenres", preferenceService.getGroupedGenres());
        model.addAttribute("selectedGenres", preferenceService.getUserSelectedGenres(userDetails));
        return "preference";
    }

    @PostMapping("/preference")
    public String savePreference(@RequestParam(required = false) List<String> genres,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        preferenceService.updateUserPreferences(userDetails, genres);
        return "redirect:/recommend";
    }
}