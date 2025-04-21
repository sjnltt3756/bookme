package com.bookme.service;

import com.bookme.dto.GenreOption;
import com.bookme.entity.GenrePreference;
import com.bookme.entity.User;
import com.bookme.repository.GenrePreferenceRepository;
import com.bookme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final GenrePreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    public Map<String, List<GenreOption>> getGroupedGenres() {
        return Map.of(
                "문학", List.of(
                        new GenreOption("소설", "소설"),
                        new GenreOption("시", "시집"),
                        new GenreOption("판타지", "판타지"),
                        new GenreOption("공포", "공포 소설"),
                        new GenreOption("로맨스", "로맨스 소설"),
                        new GenreOption("추리", "추리 소설")
                ),
                "비문학", List.of(
                        new GenreOption("철학", "철학"),
                        new GenreOption("역사", "역사"),
                        new GenreOption("자기계발", "자기계발"),
                        new GenreOption("종교", "종교"),
                        new GenreOption("건강", "건강")
                ),
                "학문/기술", List.of(
                        new GenreOption("과학", "과학"),
                        new GenreOption("기술", "기술"),
                        new GenreOption("교육", "교육"),
                        new GenreOption("비즈니스", "비즈니스")
                ),
                "기타", List.of(
                        new GenreOption("전기/회고록", "전기"),
                        new GenreOption("예술", "예술"),
                        new GenreOption("여행", "여행"),
                        new GenreOption("아동", "아동 도서"),
                        new GenreOption("만화", "만화책")
                )
        );
    }

    public void updateUserPreferences(UserDetails userDetails, List<String> genres) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        // 기존 선호 장르 삭제
        preferenceRepository.deleteAll(preferenceRepository.findByUser(user));

        // 선택된 장르가 있다면 새로 저장
        if (genres != null && !genres.isEmpty()) {
            for (String genre : genres) {
                preferenceRepository.save(GenrePreference.builder()
                        .user(user)
                        .genre(genre)
                        .build());
            }
        }
    }

    public List<String> getUserSelectedGenres(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return preferenceRepository.findByUser(user).stream()
                .map(GenrePreference::getGenre)
                .toList();
    }
}