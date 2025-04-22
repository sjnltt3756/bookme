package com.bookme.repository;

import com.bookme.entity.BookLike;
import com.bookme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookLikeRepository extends JpaRepository<BookLike, Long> {
    List<BookLike> findByUser(User user);
    Optional<BookLike> findByUserAndTitle(User user, String title); // or infoLink
}
