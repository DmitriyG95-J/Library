package com.example.mylibrary.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterReqDTO {

    @NotBlank
    @Pattern(regexp = "(?i)[a-zа-я0-9]*", message = "Имя должно состоять из букв")
    private String userName;

    @NotBlank
    @Size(min = 4, max = 24, message = "Пароль должен быть больше 8 символов, но меньше 24")
    private String password;

    @Email
    private String email;
}
