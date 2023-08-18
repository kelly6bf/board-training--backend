package site.board.boardtraining.domain.board.dto;

import site.board.boardtraining.domain.member.entity.Member;

public record UpdateBoardDto(
        Long boardId,
        String title,
        String description,
        String thumbnailImageUrl,
        Member member
) {
    public static UpdateBoardDto of(
            Long boardId,
            String title,
            String description,
            String thumbnailImageUrl,
            Member member
    ) {
        return new UpdateBoardDto(
                boardId,
                title,
                description,
                thumbnailImageUrl,
                member
        );
    }
}