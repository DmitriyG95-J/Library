package com.example.mylibrary.repository;

import com.example.mylibrary.model.File;
import com.example.mylibrary.model.FileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    File findByAuthorId (Long authorId);
}
