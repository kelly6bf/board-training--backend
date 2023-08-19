package site.board.boardtraining.domain.board.dto.business;

public record DeleteBoardDto(
        Long boardId,
        Long memberId
) {
    public static DeleteBoardDto of(
            Long boardId,
            Long memberId
    ) {
        return new DeleteBoardDto(
                boardId,
                memberId
        );
    }
}