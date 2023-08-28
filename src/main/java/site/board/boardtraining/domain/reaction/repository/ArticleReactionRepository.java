package site.board.boardtraining.domain.reaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.reaction.entity.ArticleReaction;

public interface ArticleReactionRepository
        extends JpaRepository<ArticleReaction, Long> {
}