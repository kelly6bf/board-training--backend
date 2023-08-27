package site.board.boardtraining.domain.board.dto.business;

import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.member.entity.Member;

import java.util.LinkedHashSet;

public record CreateBoardDto(
        String title,
        String description,
        String thumbnailImageUrl,
        LinkedHashSet<String> hashtags,
        Long memberId
) {
    public static CreateBoardDto of(
            String title,
            String description,
            String thumbnailImageUrl,
            LinkedHashSet<String> hashtags,
            Long memberId
    ) {
        return new CreateBoardDto(
                title,
                description,
                thumbnailImageUrl,
                hashtags,
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