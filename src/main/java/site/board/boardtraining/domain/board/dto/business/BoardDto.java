package site.board.boardtraining.domain.board.dto.business;

import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.member.dto.business.MemberDto;

import java.time.LocalDateTime;
import java.util.Set;

public record BoardDto(
        Long boardId,
        String title,
        String description,
        String thumbnailImageUrl,
        Set<String> hashtags,
        MemberDto memberDto,
        LocalDateTime createAt
) {
    public static BoardDto from(
            Board board,
            Set<String> hashtags
    ) {
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getDescription(),
                board.getThumbnailImageUrl(),
                hashtags,
                MemberDto.from(board.getMember()),
                board.getCreatedAt()
        );
    }
}