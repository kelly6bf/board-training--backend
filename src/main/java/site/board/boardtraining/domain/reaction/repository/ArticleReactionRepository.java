package site.board.boardtraining.domain.reaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.reaction.constant.ReactionType;
import site.board.boardtraining.domain.reaction.entity.ArticleReaction;

public interface ArticleReactionRepository
        extends JpaRepository<ArticleReaction, Long> {

    int countAllByTypeAndArticle(ReactionType type, Article article);

    void deleteAllByArticleAndMember(Article article, Member member);

    boolean existsByTypeAndArticleAndMember(ReactionType type, Article article, Member member);
}