package com.flutter.alloffootball.common.component.file;

import com.flutter.alloffootball.common.domain.BaseEntityImage;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.field.FieldImage;
import com.flutter.alloffootball.common.jparepository.JpaFieldImageRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final JpaFieldImageRepository fieldImageRepository;
    private final EntityManager em;


    public void editImage(MultipartFile file, BaseEntityImage imageEntity, FileType fileType) {

        if (file == null || file.getContentType() == null) return;

        String originalName = file.getOriginalFilename();
        String storeName = createName(originalName);
        String thumbnailName = createName(originalName);

        try {
            fileRepository.delete(imageEntity.getStoreName(), imageEntity.getThumbnailName(), fileType);
            fileRepository.upload(file, fileType, storeName, thumbnailName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadException(FileCode.FAILED_TO_UPLOAD_FILE);
        }

        imageEntity.setImage(originalName, storeName, thumbnailName);
    }

    public void saveFieldImages(List<MultipartFile> files, Field field) {
        if (files == null || files.isEmpty()) return;

        List<FieldImage> saveImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file == null || file.getContentType() == null) continue;
            if (file.isEmpty()) continue;

            String originalName = file.getOriginalFilename();
            String storeName = createName(originalName);
            String thumbnailName = createName(originalName);

            try {
                fileRepository.upload(file, FileType.FIELD_IMAGE, storeName, thumbnailName);
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileUploadException(FileCode.FAILED_TO_UPLOAD_FILE);
            }

            FieldImage saveBoardImage = FieldImage.builder().field(field).build();
            saveBoardImage.setImage(originalName, storeName, thumbnailName);
            saveImages.add(saveBoardImage);
        }

        fieldImageRepository.saveAll(saveImages);
    }

    public void editFieldImages(List<MultipartFile> files, Field field, List<Long> removeImages) {
        List<FieldImage> removeBoardImages = fieldImageRepository.findAllById(removeImages);

        for (FieldImage removeBoardImage : removeBoardImages) {
            fileRepository.delete(removeBoardImage.getStoreName(), removeBoardImage.getThumbnailName(), FileType.FIELD_IMAGE);
        }
        fieldImageRepository.deleteAllById(removeImages);

        saveFieldImages(files, field);
    }

    public void deleteImages(List<? extends BaseEntityImage> images) {
        for (BaseEntityImage image : images) {
            deleteImage(image);
        }
    }
    public void deleteImages(String... storeImages) {
        for (String storeImage : storeImages) {
            fieldImageRepository
                .findByStoreName(storeImage)
                .ifPresent(this::deleteImage);
        }
    }

    public void deleteImage(BaseEntityImage image) {
        fileRepository.delete(image.getStoreName(), image.getThumbnailName(), FileType.FIELD_IMAGE);
        em.remove(image);
    }

    private String createName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();

        int pos = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(pos);

        return uuid + ext;
    }
}
