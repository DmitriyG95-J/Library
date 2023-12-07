package com.example.mylibrary.controller;

import com.example.mylibrary.dto.UserDTO;
import com.example.mylibrary.dto.req.UserUpdateReqDTO;
import com.example.mylibrary.dto.resp.UserRespDTO;
import com.example.mylibrary.model.User;
import com.example.mylibrary.repository.UserRepo;
import com.example.mylibrary.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public UserRespDTO getUserInfo(@PathVariable Long id) {return userService.getUserInfo(id);}

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users;
    }

    @PatchMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserRespDTO updateCurrentUser(
            @ModelAttribute @RequestBody/*(name = "updateCurrentUserReqDTO", required = false)*/
            UserUpdateReqDTO userUpdateReqDTO, @RequestParam(name = "avatar", required = false) MultipartFile avatar
    ) {
        return userService.updateCurrentUser(userUpdateReqDTO, avatar);
    }
}
