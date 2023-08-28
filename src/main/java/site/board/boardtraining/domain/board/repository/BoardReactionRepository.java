package site.board.boardtraining.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.board.constant.BoardReactionType;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.board.entity.BoardReaction;
import site.board.boardtraining.domain.member.entity.Member;

import java.util.Optional;

public interface BoardReactionRepository
        extends JpaRepository<BoardReaction, Long> {

    int countAllByTypeAndBoard(BoardReactionType type, Board board);

    boolean existsByBoardAndMember(Board board, Member member);

    Optional<BoardReaction> findByBoardAndMember(Board board, Member member);
}