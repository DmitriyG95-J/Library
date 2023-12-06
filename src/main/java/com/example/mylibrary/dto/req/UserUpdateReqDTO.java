package com.example.mylibrary.dto.req;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateReqDTO {
    @Pattern(regexp = "(?i)[a-zа-я]*", message = "Имя должно состоять из букв")
    private String name;

    @Pattern(regexp = "(?i)[a-zа-я]*", message = "Фамилия должна состоять из букв")
    private String surname;

    @Size(max = 254, message = "Текст о себе не должен превышать 254 символов")
    private String aboutMe;

    private Boolean deleteAvatar;

    private MultipartFile avatar;
}
