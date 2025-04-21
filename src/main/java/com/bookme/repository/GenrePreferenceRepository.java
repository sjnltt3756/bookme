package com.bookme.repository;

import com.bookme.entity.GenrePreference;
import com.bookme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenrePreferenceRepository extends JpaRepository<GenrePreference, Long> {
    List<GenrePreference> findByUser(User user);
}