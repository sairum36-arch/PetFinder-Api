package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.mainProfile.FileUploadResponse;
import com.PetFinder.PetFinder.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileStorageService fileStorageService;

    @PostMapping(value = "/upload")
    public FileUploadResponse uploadFile(@RequestParam("file")MultipartFile file){
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Файл для загрузки не может быть пустым.");
        }
        String fileKey = fileStorageService.uploadFile(file);
        return new FileUploadResponse(fileKey);
    }
}
