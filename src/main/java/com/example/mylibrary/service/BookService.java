package com.example.mylibrary.service;

import com.example.mylibrary.dto.BookRespDTO;
import com.example.mylibrary.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getUserBooks(Long userId);

    void addNewBookToCurrentUser(BookRespDTO bookRespDTO);

    void removeBookFromUser(Long bookId);
}
