package site.board.boardtraining.domain.board.dto.business;

public record UpdateBoardDto(
        Long boardId,
        String title,
        String description,
        String thumbnailImageUrl,
        Long memberId
) {
    public static UpdateBoardDto of(
            Long boardId,
            String title,
            String description,
            String thumbnailImageUrl,
            Long memberId
    ) {
        return new UpdateBoardDto(
                boardId,
                title,
                description,
                thumbnailImageUrl,
                memberId
        );
    }
}