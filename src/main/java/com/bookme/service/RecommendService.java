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

    public List<Book> getRecommendations(UserDetails userDetails, String query, int page, int pageSize) {
        int startIndex = page * pageSize;

        if (query != null && !query.isBlank()) {
            return googleBooksService.searchBooks(query, startIndex, pageSize);
        } else {
            User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
            List<String> genres = preferenceRepository.findByUser(user)
                    .stream()
                    .map(g -> g.getGenre())
                    .toList();

            List<Book> books = new ArrayList<>();
            for (String genre : genres) {
                books.addAll(googleBooksService.searchBooks(genre, startIndex, pageSize));
                if (books.size() >= pageSize) break;
            }

            return books.stream().limit(pageSize).toList(); // 혹시 여러 장르일 경우 제한
        }
    }
}