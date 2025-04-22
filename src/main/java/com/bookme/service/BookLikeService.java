package com.bookme.service;

import com.bookme.entity.BookLike;
import com.bookme.entity.User;
import com.bookme.entity.enums.LikeResult;
import com.bookme.repository.BookLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookLikeService {

    private final BookLikeRepository bookLikeRepository;

    public List<BookLike> getLikedBooks(User user) {
        return bookLikeRepository.findByUser(user);
    }

    public LikeResult toggleLike(User user, String title) {
        Optional<BookLike> existing = bookLikeRepository.findByUserAndTitle(user, title);
        if (existing.isPresent()) {
            bookLikeRepository.delete(existing.get());
            return LikeResult.UNLIKED;
        } else {
            return LikeResult.LIKED; // 저장은 별도로
        }
    }

    public LikeResult likeOnly(User user, String title, String thumbnail, String infoLink, List<String> authors) {
        Optional<BookLike> existing = bookLikeRepository.findByUserAndTitle(user, title);
        if (existing.isPresent()) {
            return LikeResult.ALREADY_LIKED;
        }
        BookLike book = new BookLike();
        book.setUser(user);
        book.setTitle(title);
        book.setThumbnail(thumbnail);
        book.setInfoLink(infoLink);
        book.setAuthors(authors);
        bookLikeRepository.save(book);
        return LikeResult.LIKED;
    }

    public LikeResult unlikeOnly(User user, String title) {
        Optional<BookLike> existing = bookLikeRepository.findByUserAndTitle(user, title);
        if (existing.isPresent()) {
            bookLikeRepository.delete(existing.get());
            return LikeResult.UNLIKED;
        }
        return LikeResult.NOT_LIKED;
    }
}