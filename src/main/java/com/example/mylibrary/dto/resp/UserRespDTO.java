package com.example.mylibrary.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
