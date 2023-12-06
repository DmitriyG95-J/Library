package com.example.mylibrary.service.impl;


import com.example.mylibrary.dto.req.UserUpdateReqDTO;
import com.example.mylibrary.dto.resp.UserRespDTO;
import com.example.mylibrary.model.User;
import com.example.mylibrary.repository.UserRepo;
import com.example.mylibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;



    @Override
    public UserRespDTO getUserInfo(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("пользователь не найден");
        }
        User user = optionalUser.get();
        UserRespDTO userRespDTO = new UserRespDTO();
        userRespDTO.setId(user.getId());
        userRespDTO.setName(user.getUserName());
        userRespDTO.setEmail(user.getEmail());
        userRespDTO.setAboutMe(user.getAboutMe());
        //userRespDTO.setProfileAvatarId(user.getProfileAvatar()));

        return userRespDTO;
    }

    @Override
    public UserRespDTO updateCurrentUser(UserUpdateReqDTO updateCurrentUserReqDTO, MultipartFile avatar) {
        return null;//-------------------------------------------тут делать
    }


}
