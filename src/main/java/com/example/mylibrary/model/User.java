package com.example.mylibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String userName;

    @Column
    private String email;

    @JsonIgnore
    @Column
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_avatar_id")
    @JsonIgnore
    private File profileAvatar;


    @Column
    private String aboutMe;

    @ManyToMany
    @JoinTable(
            name = "user_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserBook> userBooks = new ArrayList<>();


    @JsonIgnore
    @Column
    private String accessToken;

}
