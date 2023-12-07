package com.example.mylibrary.service.impl;


import com.example.mylibrary.dto.FileDTO;
import com.example.mylibrary.dto.UserDTO;
import com.example.mylibrary.dto.req.UserUpdateReqDTO;
import com.example.mylibrary.dto.resp.UserRespDTO;
import com.example.mylibrary.model.File;
import com.example.mylibrary.model.FileType;
import com.example.mylibrary.model.User;
import com.example.mylibrary.repository.FileRepository;
import com.example.mylibrary.repository.UserRepo;
import com.example.mylibrary.service.FileService;
import com.example.mylibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final FileService fileService;
    private final FileRepository fileRepository;

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
        userRespDTO.setBooks(user.getBooks());
        return userRespDTO;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserRespDTO updateCurrentUser(UserUpdateReqDTO updateCurrentUserReqDTO, MultipartFile avatar) {
        User user = this.getCurrentUser();
        File file = user.getProfileAvatar();

        if (avatar != null) {
            FileDTO newAvatarDTO = fileService.save(avatar);
            File newAvatar = fileService.findById(newAvatarDTO.getId());

            if (!(FileType.IMAGE).equals(newAvatar.getFileType())) {
                throw new RuntimeException("Файл для аватарки должен быть изображением");
            } if (newAvatar.getIsDeleted()) {
                throw new RuntimeException("Файл удален");
            }
            user.setProfileAvatar(newAvatar);
            newAvatar.setIsDeleted(false);
            file.setUrl(newAvatar.getUrl());
            fileRepository.save(newAvatar);
        } if (updateCurrentUserReqDTO.getDeleteAvatar() != null) {
            file.setIsDeleted(updateCurrentUserReqDTO.getDeleteAvatar());
        } if (updateCurrentUserReqDTO.getAboutMe() != null) {
            user.setAboutMe(updateCurrentUserReqDTO.getAboutMe());
        } if (updateCurrentUserReqDTO.getUserName() != null) {
            user.setUserName(updateCurrentUserReqDTO.getUserName());
        }
        userRepo.save(user);
        UserRespDTO userRespDTO = new UserRespDTO();
        userRespDTO.setId(user.getId());
        userRespDTO.setName(user.getUserName());
        userRespDTO.setEmail(user.getEmail());
        userRespDTO.setAboutMe(user.getAboutMe());
        userRespDTO.setDeleteAvatar(file.getIsDeleted());
        userRespDTO.setProfileAvatarId(file.getIsDeleted() ? null : file.getId());
        userRespDTO.setAvatarUrl(file.getIsDeleted() ? null : file.getUrl());
        return userRespDTO;
    }
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userEmail = (UserDTO) authentication.getPrincipal();
        return userRepo.findByEmail(userEmail.getEmail()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }
}
