package com.PetFinder.PetFinder.service.FileStoragePackage;


import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.entity.PetMediaEntity;
import com.PetFinder.PetFinder.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlBuilderService {
    private final FileStorageService fileStorageService;

    public String buildPetAvatarUrl(PetEntity entity){
        if(entity == null || entity.getMediaFiles() == null || entity.getMediaFiles().isEmpty()){
            return null;
        }
        return entity.getMediaFiles().stream().filter(PetMediaEntity::isMainPhoto).findFirst().map(media -> fileStorageService.getObjectUrl(media.getUrl())).orElse(null);
    }

    public String buildUserAvatarUrl(UserEntity entity){
        if(entity == null || entity.getAvatarKey() == null || entity.getAvatarKey().isEmpty()){
            return null;
        }
        return fileStorageService.getObjectUrl(entity.getAvatarKey());
    }

}
