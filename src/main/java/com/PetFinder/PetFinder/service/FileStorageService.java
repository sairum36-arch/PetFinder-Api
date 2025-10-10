package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.config.MinioConfig;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.minio.MinioClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FileStorageService {
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value("${minio.external-url}")
    private String minioExternalUrl;

    public String uploadFile(MultipartFile file) {
        try {
            String objectKey = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectKey).stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build());
            return objectKey;
        } catch (Exception e) {
            throw new RuntimeException("ошибка при загрузке файла" + e.getMessage());
        }
    }

    public String getObjectUrl(String objectKey) {
        if (objectKey == null || objectKey.isEmpty()) {
            return null;
        }
        return minioExternalUrl + "/" + bucketName + "/" + objectKey;
    }
}