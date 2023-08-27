package site.board.boardtraining.domain.board.dto.api;

import site.board.boardtraining.domain.board.dto.business.UpdateBoardDto;

import java.util.LinkedHashSet;

public record UpdateBoardRequest(
        String title,
        String description,
        String thumbnailImageUrl,
        LinkedHashSet<String> hashtags
) {
    public UpdateBoardDto toDto(
            Long boardId,
            Long memberId
    ) {
        return UpdateBoardDto.of(
                boardId,
                title,
                description,
                thumbnailImageUrl,
                hashtags,
                memberId
        );
    }
}