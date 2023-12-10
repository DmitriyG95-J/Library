package com.example.mylibrary.controller;

import com.example.mylibrary.dto.req.UserUpdateReqDTO;
import com.example.mylibrary.dto.resp.UserRespDTO;
import com.example.mylibrary.model.User;
import com.example.mylibrary.repository.UserRepo;
import com.example.mylibrary.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepo userRepo;

    @GetMapping("/users/{id}")
    @Operation(summary = "Получить инфо о пользователе по Id с пагинацией книг")
    public UserRespDTO getUserInfo(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getUserInfo(id, pageable);
    }
    @GetMapping("/users")
    @Operation(summary = "Получить всех пользователей")
    public List<User> getAllUsers() {

        return userService.getAllUsers();//нужно еще выводить список книг
    }
    @PatchMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление пользователя")
    public UserRespDTO updateCurrentUser(
            @ModelAttribute UserUpdateReqDTO userUpdateReqDTO,
            @RequestParam(name = "avatar", required = false) MultipartFile avatar) {
        return userService.updateCurrentUser(userUpdateReqDTO, avatar);
    }
}
