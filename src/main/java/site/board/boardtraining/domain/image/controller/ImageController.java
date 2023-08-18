package site.board.boardtraining.domain.image.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.board.boardtraining.domain.image.dto.ImageUploadResponse;
import site.board.boardtraining.domain.image.service.ImageService;
import site.board.boardtraining.global.response.success.SingleSuccessApiResponse;

import java.io.IOException;

import static site.board.boardtraining.domain.image.constant.ImageType.*;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/api/images/profile-images")
    public ResponseEntity<SingleSuccessApiResponse<ImageUploadResponse>> uploadProfileImage(
            MultipartFile image
    ) throws IOException {
        return ResponseEntity.ok(
                SingleSuccessApiResponse.of(
                        "성공적으로 이미지가 업로드되었습니다.",
                        ImageUploadResponse.of(
                                imageService.uploadImage(PROFILE, image)
                        )
                )
        );
    }

    @PostMapping("/api/images/board-thumbnail-images")
    public ResponseEntity<SingleSuccessApiResponse<ImageUploadResponse>> uploadBoardThumbnailImage(
            MultipartFile image
    ) throws IOException {
        return ResponseEntity.ok(
                SingleSuccessApiResponse.of(
                        "성공적으로 이미지가 업로드되었습니다.",
                        ImageUploadResponse.of(
                                imageService.uploadImage(BOARD_THUMBNAIL, image)
                        )
                )
        );
    }

    @PostMapping("/api/images/article-content-images")
    public ResponseEntity<SingleSuccessApiResponse<ImageUploadResponse>> uploadArticleContentImage(
            MultipartFile image
    ) throws IOException {
        return ResponseEntity.ok(
                SingleSuccessApiResponse.of(
                        "성공적으로 이미지가 업로드되었습니다.",
                        ImageUploadResponse.of(
                                imageService.uploadImage(ARTICLE_CONTENT, image)
                        )
                )
        );
    }

    @PostMapping("/api/images/article-comment-content-images")
    public ResponseEntity<SingleSuccessApiResponse<ImageUploadResponse>> uploadArticleCommentContentImage(
            MultipartFile image
    ) throws IOException {
        return ResponseEntity.ok(
                SingleSuccessApiResponse.of(
                        "성공적으로 이미지가 업로드되었습니다.",
                        ImageUploadResponse.of(
                                imageService.uploadImage(ARTICLE_COMMENT_CONTENT, image)
                        )
                )
        );
    }
}