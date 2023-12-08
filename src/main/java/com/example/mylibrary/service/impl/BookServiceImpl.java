package com.example.mylibrary.service.impl;

import com.example.mylibrary.dto.BookRespDTO;
import com.example.mylibrary.dto.UserDTO;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.User;
import com.example.mylibrary.repository.BookRepo;
import com.example.mylibrary.repository.UserRepo;
import com.example.mylibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final UserRepo userRepo;
    private final BookRepo bookRepo;
    @Override
    public List<Book> getUserBooks(Long userId) {
        Optional<User> optionalUser = userRepo.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getBooks();
        } else {
            throw new RuntimeException("Пользователь не найден");
        }
    }

    @Override
    public void addNewBookToCurrentUser(BookRespDTO bookRespDTO) {
        try {
            User currentUser =  getCurrentUser();
            if (!isBookExists(bookRespDTO.getTitle())) {
                Book newBook = new Book();
                newBook.setTitle(bookRespDTO.getTitle());
                newBook.setAuthors(bookRespDTO.getAuthors());
                newBook.setRating(0);
                currentUser.getBooks().add(newBook);
                newBook.getUsers().add(currentUser);
                newBook.setDescription(bookRespDTO.getDescription());
                newBook.setDateAdded(LocalDateTime.now());
                newBook.setPageCount(bookRespDTO.getPageCount());
                newBook.setUrlAvatar(bookRespDTO.getUrlAvatar());

                bookRepo.save(newBook);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //return bookRespDTO;
    }

    @Override
    public void removeBookFromUser(Long bookId) {
        try {
            User currentUser = getCurrentUser();
            Optional<Book> optionalBook = bookRepo.findById(bookId);
            if (optionalBook.isPresent()) {
                Book bookToRemove = optionalBook.get();
                if (isCurrentUserOwner(bookToRemove)) {
                    currentUser.getBooks().remove(bookToRemove);
                    bookToRemove.getUsers().remove(currentUser);
                    bookRepo.save(bookToRemove);
                } else {
                    throw new RuntimeException("Вы не являетесь владельцем этой книги");
                }
            } else {
                throw new RuntimeException("Книга с заданным ID не найдена");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isCurrentUserOwner(Book book) {
        User currentUser = getCurrentUser();
        return book.getUsers().stream()
                .anyMatch(user -> user.getId() == currentUser.getId());
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userEmail = (UserDTO) authentication.getPrincipal();
        return userRepo.findByEmail(userEmail.getEmail()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public boolean isBookExists(String title) {
        return bookRepo.existsByTitle(title);
    }
}