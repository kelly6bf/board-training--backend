package site.board.boardtraining.domain.board.dto;

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