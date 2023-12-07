package com.example.mylibrary.dto.resp;

import com.example.mylibrary.model.Book;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRespDTO {

    private Long id;

    private String name;

    private String email;

    private String aboutMe;

    private Long profileAvatarId;

    private String avatarUrl;

    private Boolean deleteAvatar;

    private List<Book> books = new ArrayList<>();
}
