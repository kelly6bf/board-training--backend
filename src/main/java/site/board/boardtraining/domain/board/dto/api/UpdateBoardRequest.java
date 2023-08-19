package site.board.boardtraining.domain.board.dto.api;

import site.board.boardtraining.domain.board.dto.business.UpdateBoardDto;

public record UpdateBoardRequest(
        String title,
        String description,
        String thumbnailImageUrl
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
                memberId
        );
    }
}