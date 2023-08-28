package site.board.boardtraining.domain.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.article.constant.ArticleReactionType;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.article.entity.ArticleReaction;

import java.util.Optional;

public interface ArticleReactionRepository
        extends JpaRepository<ArticleReaction, Long> {

    int countAllByTypeAndArticle(ArticleReactionType type, Article article);

    boolean existsByTypeAndArticleAndMember(ArticleReactionType type, Article article, Member member);

    Optional<ArticleReaction> findByArticleAndMember(Article article, Member member);
}