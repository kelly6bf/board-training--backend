package site.board.boardtraining.domain.reaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.reaction.entity.BoardReaction;

public interface BoardReactionRepository
        extends JpaRepository<BoardReaction, Long> {
}