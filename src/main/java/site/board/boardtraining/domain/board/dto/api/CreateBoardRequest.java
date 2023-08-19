package site.board.boardtraining.domain.board.dto.api;

import site.board.boardtraining.domain.board.dto.business.CreateBoardDto;

public record CreateBoardRequest(
        String title,
        String description,
        String thumbnailImageUrl
) {
    public CreateBoardDto toDto(
            Long memberId
    ) {
        return CreateBoardDto.of(
                title,
                description,
                thumbnailImageUrl,
                memberId
        );
    }
}