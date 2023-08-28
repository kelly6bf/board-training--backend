package site.board.boardtraining.domain.board.service;

import site.board.boardtraining.domain.board.entity.Board;

public interface BoardReactionService {

    int getBoardLikeCount(Board board);

    void addBoardLike(Long boardId, Long memberId);

    void deleteBoardLike(Long boardId, Long memberId);

    int getBoardDislikeCount(Board board);

    void addBoardDislike(Long boardId, Long memberId);

    void deleteBoardDislike(Long boardId, Long memberId);
}