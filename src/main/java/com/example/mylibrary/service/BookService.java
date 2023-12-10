package com.example.mylibrary.service;

import com.example.mylibrary.dto.BookRespDTO;
import com.example.mylibrary.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Page<Book> getUserBooks(Long userId, Pageable pageable);

    void addNewBookToCurrentUser(BookRespDTO bookRespDTO);

    void addBookFromAnotherUser(Long bookId);

    void removeBookFromUser(Long bookId);

    void addBookToFavorite(Long bookId);

    void removeBookFromFavorite(Long bookId);

    void addBookToRead(Long bookId);

    void removeBookFromRead(Long bookId);

    void rateBook(Long bookId, Float userRate);
}
