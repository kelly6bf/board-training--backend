package site.board.boardtraining.domain.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.article.constant.ArticleReactionType;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.article.entity.ArticleReaction;

public interface ArticleReactionRepository
        extends JpaRepository<ArticleReaction, Long> {

    int countAllByTypeAndArticle(ArticleReactionType type, Article article);

    void deleteAllByArticleAndMember(Article article, Member member);

    boolean existsByTypeAndArticleAndMember(ArticleReactionType type, Article article, Member member);
}