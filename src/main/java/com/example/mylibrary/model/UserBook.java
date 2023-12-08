package com.example.mylibrary.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_books")
@Data
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "is_favorite")
    private Boolean isFavorite;
}
