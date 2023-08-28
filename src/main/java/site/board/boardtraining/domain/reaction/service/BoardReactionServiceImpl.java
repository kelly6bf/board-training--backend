package site.board.boardtraining.domain.reaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.reaction.constant.ReactionType;
import site.board.boardtraining.domain.reaction.entity.BoardReaction;
import site.board.boardtraining.domain.reaction.exception.ReactionBusinessException;
import site.board.boardtraining.domain.reaction.repository.BoardReactionRepository;

import static site.board.boardtraining.domain.reaction.constant.ReactionType.DISLIKE;
import static site.board.boardtraining.domain.reaction.constant.ReactionType.LIKE;
import static site.board.boardtraining.domain.reaction.exception.ReactionErrorCode.*;

@Transactional
@Service
public class BoardReactionServiceImpl
        implements BoardReactionService {

    private final BoardReactionRepository boardReactionRepository;

    public BoardReactionServiceImpl(BoardReactionRepository boardReactionRepository) {
        this.boardReactionRepository = boardReactionRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public int getBoardLikeCount(Board board) {
        return boardReactionRepository.countAllByTypeAndBoard(
                LIKE,
                board
        );
    }

    @Override
    public void addBoardLike(Board board) {

        if (checkReactionExistence(LIKE, board))
            throw new ReactionBusinessException(LIKE_REACTION_ALREADY_EXIST);

        boardReactionRepository.save(
                BoardReaction.of(
                        LIKE,
                        board,
                        board.getMember()
                )
        );
    }

    @Override
    public void deleteBoardLike(Board board) {

        if (!checkReactionExistence(LIKE, board))
            throw new ReactionBusinessException(LIKE_REACTION_NOT_FOUND);

        boardReactionRepository.deleteAllByBoardAndMember(
                board,
                board.getMember()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public int getBoardDislikeCount(Board board) {
        return boardReactionRepository.countAllByTypeAndBoard(
                DISLIKE,
                board
        );
    }

    @Override
    public void addBoardDislike(Board board) {
        if (checkReactionExistence(DISLIKE, board))
            throw new ReactionBusinessException(DISLIKE_REACTION_ALREADY_EXIST);

        boardReactionRepository.save(
                BoardReaction.of(
                        DISLIKE,
                        board,
                        board.getMember()
                )
        );
    }

    @Override
    public void deleteBoardDislike(Board board) {
        if (!checkReactionExistence(DISLIKE, board))
            throw new ReactionBusinessException(DISLIKE_REACTION_NOT_FOUND);

        boardReactionRepository.deleteAllByBoardAndMember(
                board,
                board.getMember()
        );
    }

    private boolean checkReactionExistence(
            ReactionType reactionType,
            Board board
    ) {
        return boardReactionRepository.existsByTypeAndBoardAndMember(
                reactionType,
                board,
                board.getMember()
        );
    }
}