package site.board.boardtraining.domain.board.dto.api;

public record CreateBoardResponse(
        Long boardId
) {
    public static CreateBoardResponse of(
            Long boardId
    ) {
        return new CreateBoardResponse(boardId);
    }
}