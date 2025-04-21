package com.bookme.service;

import com.bookme.dto.Book;
import com.bookme.entity.User;
import com.bookme.repository.GenrePreferenceRepository;
import com.bookme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final GoogleBooksService googleBooksService;
    private final GenrePreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    public List<Book> getRecommendations(UserDetails userDetails, String query) {
        if (query != null && !query.isBlank()) {
            // 검색어 기반 추천
            return googleBooksService.searchBooks(query);
        } else {
            // 선호 장르 기반 추천
            User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
            List<String> genres = preferenceRepository.findByUser(user)
                    .stream()
                    .map(g -> g.getGenre())
                    .toList();

            List<Book> books = new ArrayList<>();
            for (String genre : genres) {
                books.addAll(googleBooksService.searchBooks(genre));
            }
            return books;
        }
    }
}