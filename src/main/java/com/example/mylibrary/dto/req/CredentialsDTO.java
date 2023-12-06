package com.example.mylibrary.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CredentialsDTO {

    @Email
    private String email;

    @NotBlank
    private String password;
}
