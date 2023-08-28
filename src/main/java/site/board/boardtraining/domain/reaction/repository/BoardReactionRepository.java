package site.board.boardtraining.domain.reaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.reaction.constant.ReactionType;
import site.board.boardtraining.domain.reaction.entity.BoardReaction;

public interface BoardReactionRepository
        extends JpaRepository<BoardReaction, Long> {

    int countAllByTypeAndBoard(ReactionType type, Board board);

    int deleteAllByBoardAndMember(Board board, Member member);

    boolean existsByTypeAndBoardAndMember(ReactionType type, Board board, Member member);
}