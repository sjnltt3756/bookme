package com.bookme.service;

import com.bookme.dto.Book;
import com.bookme.entity.User;
import com.bookme.repository.GenrePreferenceRepository;
import com.bookme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final GoogleBooksService googleBooksService;
    private final GenrePreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    public List<Book> getRecommendations(UserDetails userDetails, String query, String category, int page, int pageSize) {
        int startIndex = page * pageSize;

        if (query != null && !query.isBlank()) {
            String fullQuery = category.equals("author") ? "inauthor:" + query : "intitle:" + query;
            return googleBooksService.searchBooks(fullQuery, startIndex, pageSize);
        } else if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
            if (user != null) {
                List<String> genres = preferenceRepository.findByUser(user)
                        .stream()
                        .map(g -> g.getGenre())
                        .toList();

                List<Book> books = new ArrayList<>();
                for (String genre : genres) {
                    List<Book> genreBooks = googleBooksService.searchBooks(genre, startIndex, pageSize);
                    for (Book book : genreBooks) {
                        books.add(book);
                        if (books.size() >= pageSize) break;
                    }
                    if (books.size() >= pageSize) break;
                }

                return books.stream().limit(pageSize).toList();
            }
        }

        // 로그인하지 않은 사용자이거나 장르가 없는 경우: 인기 키워드로 기본 추천 제공
        return googleBooksService.searchBooks("베스트셀러", startIndex, pageSize);
    }
}