package com.example.mylibrary.service.impl;

import com.example.mylibrary.dto.FileDTO;
import com.example.mylibrary.model.File;
import com.example.mylibrary.model.FileType;
import com.example.mylibrary.model.User;
import com.example.mylibrary.repository.FileRepository;
import com.example.mylibrary.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    @Override
    public FileDTO save(MultipartFile file) {
        return null;
    }

    @Override
    public File findById(Long id) {
        return null;
    }

    @Override
    public void setAvatar(User user, File newAvatar) {
        Long userId = user.getId();

        // Получаем текущий аватар пользователя (если он есть)
        File currentAvatar = fileRepository.findByAuthorId(userId);

        // Если у пользователя уже есть аватар, заменяем его
        if (currentAvatar != null) {
            currentAvatar.setFileType(FileType.IMAGE); // Присваиваем текущему файлу другой тип
            fileRepository.save(currentAvatar);
        }

        // Устанавливаем новый аватар для пользователя
        newAvatar.setFileType(FileType.IMAGE);
        newAvatar.setAuthor(user);
        fileRepository.save(newAvatar);
    }
}
