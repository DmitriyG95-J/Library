package com.example.mylibrary.service.impl;

import com.example.mylibrary.dto.BookRespDTO;
import com.example.mylibrary.dto.UserDTO;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.User;
import com.example.mylibrary.model.UserBook;
import com.example.mylibrary.repository.BookRepo;
import com.example.mylibrary.repository.UserBookRepo;
import com.example.mylibrary.repository.UserRepo;
import com.example.mylibrary.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final UserRepo userRepo;
    private final BookRepo bookRepo;
    private final UserBookRepo userBookRepo;
//    @Override
//    public List<Book> getUserBooks(Long userId) {
//        Optional<User> optionalUser = userRepo.findById(userId);
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            return user.getBooks();
//        } else {
//            throw new RuntimeException("Пользователь не найден");
//        }
//    }
@Override
public Page<Book> getUserBooks(Long userId, Pageable pageable) {
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

    List<Book> userBooks = user.getBooks();
    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), userBooks.size());

    return new PageImpl<>(userBooks.subList(start, end), pageable, userBooks.size());
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
                newBook.setNumbersOfVoters(0);
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
    public void addBookFromAnotherUser(Long bookId) {
        User currentUser = getCurrentUser();
        Optional<Book> optionalBook = bookRepo.findById(bookId);
        if (currentUser != null && optionalBook.isPresent()) {
            Book bookToAdd = optionalBook.get();
            if (currentUser.getBooks().stream().noneMatch(b -> b.getBookId() == bookToAdd.getBookId())) {
                currentUser.getBooks().add(bookToAdd);
                userRepo.save(currentUser);
            } else {
                throw new RuntimeException("Книга уже добавлена пользователю");
            }
        } else {
            throw new RuntimeException("Книга не найдена");
        }
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

    @Override
    public void addBookToFavorite(Long bookId) {
        try {
            User currentUser = getCurrentUser();
            Book book = bookRepo.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Книга с указанным ID не найдена"));

            UserBook userBook = userBookRepo.findByUserIdAndBook(currentUser.getId(), book);
            if (userBook != null) {
                userBook.setIsFavorite(true);
                userBookRepo.save(userBook);
            } else {
                throw new RuntimeException("Книга с указанным ID не найдена у пользователя");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeBookFromFavorite(Long bookId) {
        try {
            User currentUser = getCurrentUser();

            Book book = bookRepo.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Книга с указанным ID не найдена"));
            UserBook userBook = userBookRepo.findByUserIdAndBook(currentUser.getId(), book);
            if (userBook != null) {
                userBook.setIsFavorite(false);
                userBookRepo.save(userBook);
            } else {
                throw new RuntimeException("Книга с указанным ID не найдена у пользователя");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addBookToRead(Long bookId) {
        try {
            User currentUser = getCurrentUser();

            Book book = bookRepo.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Книга с указанным ID не найдена"));
            UserBook userBook = userBookRepo.findByUserIdAndBook(currentUser.getId(), book);
            if (userBook != null) {
                userBook.setIsRead(true);
                userBookRepo.save(userBook);
            } else {
                throw new RuntimeException("Книга с указанным ID не найдена у пользователя");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeBookFromRead(Long bookId) {
        try {
            User currentUser = getCurrentUser();

            Book book = bookRepo.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Книга с указанным ID не найдена"));
            UserBook userBook = userBookRepo.findByUserIdAndBook(currentUser.getId(), book);
            if (userBook != null) {
                userBook.setIsRead(false);
                userBookRepo.save(userBook);
            } else {
                throw new RuntimeException("Книга с указанным ID не найдена у пользователя");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rateBook(Long bookId, Float userRate) {
        if (userRate < 1 || userRate > 5) {
            throw new IllegalArgumentException("Оценка должна быть в пределах от 1 до 5.");
        }
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        BigDecimal currentRating = BigDecimal.valueOf(book.getRating());
        BigDecimal currentNumberOfVoters = BigDecimal.valueOf(book.getNumbersOfVoters());

        BigDecimal newRating = currentRating.multiply(currentNumberOfVoters)
                .add(BigDecimal.valueOf(userRate))
                .divide(currentNumberOfVoters.add(BigDecimal.ONE), 2, BigDecimal.ROUND_HALF_UP);

        book.setRating(newRating.floatValue());
        book.setNumbersOfVoters(currentNumberOfVoters.add(BigDecimal.ONE).floatValue());
        bookRepo.save(book);
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