package com.example.mylibrary.service.impl;

import com.example.mylibrary.configuration.utils.MimeTypesUtil;
import com.example.mylibrary.dto.FileDTO;
import com.example.mylibrary.model.File;
import com.example.mylibrary.model.FileType;
import com.example.mylibrary.model.User;
import com.example.mylibrary.repository.FileRepository;
import com.example.mylibrary.repository.UserRepo;
import com.example.mylibrary.service.FileService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private Random random = new Random();
    private final UserRepo userRepo;

    @Value("${local.file.path}")
    private String uploadPath;

    @Value("${remote.file.path}")
    private String remoteUrl;

    private final FileRepository fileRepository;
    @Override
    public FileDTO save(MultipartFile file) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
                createDirectoriesForDiffFileTypes();
            }

            String fileName = random.nextInt(Integer.MAX_VALUE) + file.getOriginalFilename();

            Tika tika = new Tika();
            String mimeType = tika.detect(fileName);
            String extension = MimeTypesUtil.lookupExt(mimeType);
            if (extension == null) {
                throw new BadRequestException("не допустимый тип расширения");
            }
            File newFile = new File();
            for (FileType type : FileType.values()) {
                if (type.getAviableExtensions().contains(extension)) {
                    newFile.setFileType(type);
                    break;
                }
            }
            if (null == newFile.getFileType()) {
                newFile.setFileType(FileType.FILE);
            }

            String subDirName = newFile.getFileType().name().toLowerCase();
            newFile.setRemoteUrl(remoteUrl + "/" + subDirName + "/" + fileName);
            Path pathToLocalFile = root.resolve(subDirName).resolve(fileName);

            newFile.setUrl(pathToLocalFile.toString());
            newFile.setExtension(extension);
            newFile.setFileName(fileName);
            newFile.setAuthor(getCurrentUser());
            newFile.setIsDeleted(false);
            newFile = fileRepository.save(newFile);

            Files.copy(file.getInputStream(), pathToLocalFile);

            FileDTO fileDTO = new FileDTO();
            fileDTO.setRemoteUrl(newFile.getRemoteUrl());
            fileDTO.setId(newFile.getId());
            fileDTO.setExtension(newFile.getExtension());
            fileDTO.setFileName(fileName);
            fileDTO.setAuthorId(newFile.getAuthor().getId());
            fileDTO.setIsDeleted(false);

            return fileDTO;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + email));
    }

    private void createDirectoriesForDiffFileTypes() throws IOException {
        for (FileType fileType : FileType.values()) {
            String name = fileType.name().toLowerCase();
            Path path = Paths.get(uploadPath).resolve(name);
            if (!Files.exists(path)) {
                Files.createDirectories(Paths.get(uploadPath).resolve(name));
            }
        }
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));

        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
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
