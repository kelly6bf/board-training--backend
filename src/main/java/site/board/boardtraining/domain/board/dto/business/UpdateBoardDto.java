package site.board.boardtraining.domain.board.dto.business;

import java.util.Set;

public record UpdateBoardDto(
        Long boardId,
        String title,
        String description,
        String thumbnailImageUrl,
        Set<String> hashtags,
        Long memberId
) {
    public static UpdateBoardDto of(
            Long boardId,
            String title,
            String description,
            String thumbnailImageUrl,
            Set<String> hashtags,
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