package site.board.boardtraining.domain.board.dto.business;

import java.util.LinkedHashSet;

public record UpdateBoardDto(
        Long boardId,
        String title,
        String description,
        String thumbnailImageUrl,
        LinkedHashSet<String> hashtags,
        Long memberId
) {
    public static UpdateBoardDto of(
            Long boardId,
            String title,
            String description,
            String thumbnailImageUrl,
            LinkedHashSet<String> hashtags,
            Long memberId
    ) {
        return new UpdateBoardDto(
                boardId,
                title,
                description,
                thumbnailImageUrl,
                hashtags,
                memberId
        );
    }
}