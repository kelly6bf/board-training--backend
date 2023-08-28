package site.board.boardtraining.domain.board.service;

import site.board.boardtraining.domain.board.entity.Board;

public interface BoardReactionService {

    int getBoardLikeCount(Board board);

    void addBoardLike(Board board);

    void deleteBoardLike(Board board);

    int getBoardDislikeCount(Board board);

    void addBoardDislike(Board board);

    void deleteBoardDislike(Board board);
}