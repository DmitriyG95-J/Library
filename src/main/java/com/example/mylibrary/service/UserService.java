package com.example.mylibrary.service;

import com.example.mylibrary.dto.UserDTO;
import com.example.mylibrary.dto.req.UserUpdateReqDTO;
import com.example.mylibrary.dto.resp.UserRespDTO;
import com.example.mylibrary.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {

    UserRespDTO getUserInfo(Long id);

    List<User> getAllUsers();

    UserRespDTO updateCurrentUser(UserUpdateReqDTO updateCurrentUserReqDTO, MultipartFile avatar);
}

