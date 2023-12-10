package com.example.mylibrary.service;

import com.example.mylibrary.dto.FileDTO;
import com.example.mylibrary.model.File;
import com.example.mylibrary.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileService {
    FileDTO save(MultipartFile file);

    File findById(Long id);

    void setAvatar(User user, File newAvatar);
}
