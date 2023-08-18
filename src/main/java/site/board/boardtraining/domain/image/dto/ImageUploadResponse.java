package site.board.boardtraining.domain.image.dto;

public record ImageUploadResponse(
        String imageUrl
) {
    public static ImageUploadResponse of(
            String imageUrl
    ) {
        return new ImageUploadResponse(imageUrl);
    }
}