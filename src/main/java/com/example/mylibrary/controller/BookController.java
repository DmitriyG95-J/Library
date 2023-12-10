package com.example.mylibrary.controller;

import com.example.mylibrary.dto.BookRespDTO;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.repository.BookRepo;
import com.example.mylibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookRepo bookRepo;
    private final BookService bookService;

    @GetMapping("/users/{userId}")
    public Page<Book> getUserBooks(@PathVariable Long userId, Pageable pageable) {
        return bookService.getUserBooks(userId, pageable);
    }
    @PostMapping("/addBook")
    public void addNewBookToCurrentUser(@RequestBody BookRespDTO bookRespDTO) {
        bookService.addNewBookToCurrentUser(bookRespDTO);
    }
    @PatchMapping("/addBook/{bookId}")
    public void addBookFromAnotherUser(@PathVariable Long bookId) {
        bookService.addBookFromAnotherUser(bookId);
    }
    @DeleteMapping("/removeBook/{bookId}")
    public void removeBookFromUser(@PathVariable Long bookId) {

        bookService.removeBookFromUser(bookId);
    }
    @PatchMapping("/addToFavorite/{bookId}")
    public void addBookToFavorite(@PathVariable Long bookId) {
        bookService.addBookToFavorite(bookId);
    }
    @PatchMapping("/removeFromFavorite/{bookId}")
    public void removeBookFromFavorite(@PathVariable Long bookId) {
        bookService.removeBookFromFavorite(bookId);
    }

    @PatchMapping("/addToRead/{bookId}")
    public void addBookToRead(@PathVariable Long bookId) {
        bookService.addBookToRead(bookId);
    }

    @PatchMapping("/removeFromRead/{bookId}")
    public void removeBookFromRead(@PathVariable Long bookId) {
        bookService.removeBookFromRead(bookId);
    }

    @PatchMapping("/rateBook/{bookId}")
    public void rateBook(@PathVariable Long bookId, @RequestBody Float userRate) {
        bookService.rateBook(bookId, userRate);
    }
}
