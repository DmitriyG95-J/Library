package com.example.mylibrary.controller;

import com.example.mylibrary.dto.BookRespDTO;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.repository.BookRepo;
import com.example.mylibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookRepo bookRepo;
    private final BookService bookService;


    @GetMapping("/users/{userId}")
    public List<Book> getUserBooks(@PathVariable Long userId) {
        return bookService.getUserBooks(userId);
    }

    @PostMapping("/addBook")
    public void addNewBookToCurrentUser(@RequestBody BookRespDTO bookRespDTO) {
        bookService.addNewBookToCurrentUser(bookRespDTO);
    }
    @GetMapping("/removeBook/{bookId}")
    public void removeBookFromUser(@PathVariable Long bookId) {
        bookService.removeBookFromUser(bookId);
    }

    public void addBookToFavorite(@)
}
