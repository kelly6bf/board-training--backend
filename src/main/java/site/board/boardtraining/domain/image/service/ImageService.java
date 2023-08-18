package site.board.boardtraining.domain.image.service;

import org.springframework.web.multipart.MultipartFile;
import site.board.boardtraining.domain.image.constant.ImageType;

import java.io.IOException;

public interface ImageService {

    String uploadImage(ImageType imageType, MultipartFile image) throws IOException;

    void deleteImage(String imagePath);
}