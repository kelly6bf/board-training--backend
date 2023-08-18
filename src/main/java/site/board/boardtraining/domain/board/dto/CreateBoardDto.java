package site.board.boardtraining.domain.board.dto;

import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.member.entity.Member;

public record CreateBoardDto(
        String title,
        String description,
        String thumbnailImageUrl,
        Member member
) {
    public static CreateBoardDto of(
            String title,
            String description,
            String thumbnailImageUrl,
            Member member
    ) {
        return new CreateBoardDto(
                title,
                description,
                thumbnailImageUrl,
                member
        );
    }

    public Board toEntity() {
        return Board.of(
                title,
                description,
                thumbnailImageUrl,
                member
        );
    }
}