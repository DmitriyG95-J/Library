package com.example.mylibrary.repository;

import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long id);

    Boolean existsByTitle(String title);
    Page<Book> findByUsers(User user, Pageable pageable);
}
