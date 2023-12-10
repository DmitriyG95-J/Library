package com.example.mylibrary.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"users", "userBooks"})
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

    @Column
    @Range(min = 0, max = 10000000)
    private float numbersOfVoters;

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
    @JsonBackReference
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<UserBook> userBooks = new ArrayList<>();
}