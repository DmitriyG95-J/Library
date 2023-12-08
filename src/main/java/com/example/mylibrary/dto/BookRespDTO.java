package com.example.mylibrary.dto;

import com.example.mylibrary.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookRespDTO {
    private long bookId;
    private String title;
    private String description;
    private String urlAvatar;
    private float rating;
    private List<String> authors;
    private int pageCount;
    private LocalDateTime dateAdded;
    private Boolean isRead;
    private Boolean isFavorite;
    private List<User> users = new ArrayList<>();
}
