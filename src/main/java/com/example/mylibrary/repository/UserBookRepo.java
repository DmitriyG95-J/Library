package com.example.mylibrary.repository;

import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.User;
import com.example.mylibrary.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBookRepo extends JpaRepository<UserBook, Long> {
    UserBook findByUserIdAndBook(Long userId, Book book);
    UserBook findByUserId(Long userId);
}
