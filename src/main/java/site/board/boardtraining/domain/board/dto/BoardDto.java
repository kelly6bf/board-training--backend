package site.board.boardtraining.domain.board.dto;

import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.member.dto.business.MemberDto;

import java.time.LocalDateTime;

public record BoardDto(
        Long boardId,
        String title,
        String description,
        String thumbnailImageUrl,
        MemberDto memberDto,
        LocalDateTime createAt
) {
    public static BoardDto from(
            Board board
    ) {
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getDescription(),
                board.getThumbnailImageUrl(),
                MemberDto.from(board.getMember()),
                board.getCreatedAt()
        );
    }
}