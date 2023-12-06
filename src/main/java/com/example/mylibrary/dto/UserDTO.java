package com.example.mylibrary.dto;

import com.example.mylibrary.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private String aboutMe;

    private Long profileAvatarId;

    private String avatarUrl;

    private String token;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getUserName();
        this.email = user.getEmail();
        this.aboutMe = user.getAboutMe();
        this.profileAvatarId = (user.getProfileAvatar() != null) ? user.getProfileAvatar().getId() : null;
        this.avatarUrl = (user.getProfileAvatar() != null) ? user.getProfileAvatar().getUrl() : null;
        this.token = null;
    }

}
