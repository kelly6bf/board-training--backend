package site.board.boardtraining.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.board.constant.BoardReactionType;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.board.entity.BoardReaction;
import site.board.boardtraining.domain.member.entity.Member;

public interface BoardReactionRepository
        extends JpaRepository<BoardReaction, Long> {

    int countAllByTypeAndBoard(BoardReactionType type, Board board);

    void deleteAllByBoardAndMember(Board board, Member member);

    boolean existsByTypeAndBoardAndMember(BoardReactionType type, Board board, Member member);
}