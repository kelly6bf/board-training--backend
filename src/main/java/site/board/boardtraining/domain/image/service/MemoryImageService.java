package site.board.boardtraining.domain.image.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.board.boardtraining.domain.image.constant.ImageType;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class MemoryImageService
        implements ImageService{

    private static final String ROOT_PATH = "/Users/kmo/project/training/board-training/storage";

    @Override
    public String uploadImage(
            ImageType imageType,
            MultipartFile image
    ) throws IOException {
        String uploadImageFullPath =
                ROOT_PATH + imageType.getPath() + "/" + getStoredImageName(image.getOriginalFilename());

        image.transferTo(new File(uploadImageFullPath));
        return uploadImageFullPath;
    }

    @Override
    public void deleteImage(String storedImagePath) {
        File deleteFile = new File(storedImagePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    private String getStoredImageName(
            String originalFileName
    ) {
        String uuid = UUID.randomUUID().toString();
        String imageExtension = originalFileName
                .substring(originalFileName.lastIndexOf(".") + 1);
        return uuid + "." + imageExtension;
    }
}