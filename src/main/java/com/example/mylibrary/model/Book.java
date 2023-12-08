package com.example.mylibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String urlAvatar;

    @Column
    @Range(min = 0, max = 5)
    private float rating;

    @ElementCollection
    private List<String> authors;

    //    BookDTO bookDTO = new BookDTO();
//bookDTO.setTitle("Название книги");
//bookDTO.setAuthors(Arrays.asList("Автор 1", "Автор 2", "Автор 3")); ----------это вставить в код где будет логика
    @Column
    private int pageCount;

    @Column
    private LocalDateTime dateAdded;

    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<UserBook> userBooks = new ArrayList<>();
}