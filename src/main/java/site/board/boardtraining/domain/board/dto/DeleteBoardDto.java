package site.board.boardtraining.domain.board.dto;

import site.board.boardtraining.domain.member.entity.Member;

public record DeleteBoardDto(
        Long boardId,
        Member member
) {
    public static DeleteBoardDto of(
            Long boardId,
            Member member
    ) {
        return new DeleteBoardDto(
                boardId,
                member
        );
    }
}