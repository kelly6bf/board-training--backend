package site.board.boardtraining.domain.board.dto;

import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.member.entity.Member;

public record CreateBoardDto(
        String title,
        String description,
        String thumbnailImageUrl,
        Long memberId
) {
    public static CreateBoardDto of(
            String title,
            String description,
            String thumbnailImageUrl,
            Long memberId
    ) {
        return new CreateBoardDto(
                title,
                description,
                thumbnailImageUrl,
                memberId
        );
    }

    public Board toEntity(Member member) {
        return Board.of(
                title,
                description,
                thumbnailImageUrl,
                member
        );
    }
}