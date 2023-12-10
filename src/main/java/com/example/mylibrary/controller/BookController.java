package com.example.mylibrary.controller;

import com.example.mylibrary.dto.BookRespDTO;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.repository.BookRepo;
import com.example.mylibrary.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookRepo bookRepo;
    private final BookService bookService;

    @GetMapping("/users/{userId}")
    @Operation(summary = "получение книг пользователя по Id")
    public Page<Book> getUserBooks(@PathVariable Long userId, Pageable pageable) {
        return bookService.getUserBooks(userId, pageable);
    }
    @PostMapping("/addBook")
    @Operation(summary = "Добавление книги к пользователю")
    public void addNewBookToCurrentUser(@RequestBody BookRespDTO bookRespDTO) {
        bookService.addNewBookToCurrentUser(bookRespDTO);
    }
    @PatchMapping("/addBook/{bookId}")
    @Operation(summary = "Добавление книги к текущему пользователю от другого пользователя")
    public void addBookFromAnotherUser(@PathVariable Long bookId) {
        bookService.addBookFromAnotherUser(bookId);
    }
    @DeleteMapping("/removeBook/{bookId}")
    @Operation(summary = "Удалить книгу из библиотеки текущего пользователя")
    public void removeBookFromUser(@PathVariable Long bookId) {

        bookService.removeBookFromUser(bookId);
    }
    @PatchMapping("/addToFavorite/{bookId}")
    @Operation(summary = "Добавить книгу в избранное")
    public void addBookToFavorite(@PathVariable Long bookId) {
        bookService.addBookToFavorite(bookId);
    }
    @PatchMapping("/removeFromFavorite/{bookId}")
    @Operation(summary = "Удалить книгу из избранного")
    public void removeBookFromFavorite(@PathVariable Long bookId) {
        bookService.removeBookFromFavorite(bookId);
    }

    @PatchMapping("/addToRead/{bookId}")
    @Operation(summary = "Добаить книгу в прочитанное")
    public void addBookToRead(@PathVariable Long bookId) {
        bookService.addBookToRead(bookId);
    }

    @PatchMapping("/removeFromRead/{bookId}")
    @Operation(summary = "Удалить книгу из прочитанного")
    public void removeBookFromRead(@PathVariable Long bookId) {
        bookService.removeBookFromRead(bookId);
    }

    @PatchMapping("/rateBook/{bookId}")
    @Operation(summary = "Поставить оценку книге")
    public void rateBook(@PathVariable Long bookId, @RequestBody Float userRate) {
        bookService.rateBook(bookId, userRate);
    }
}
