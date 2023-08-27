package site.board.boardtraining.domain.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.hashtag.entity.ArticleHashtag;

import java.util.Set;

public interface ArticleHashtagRepository
        extends JpaRepository<ArticleHashtag, Long> {

    Set<ArticleHashtag> findAllByArticle(Article article);

    void deleteAllByArticle(Article article);
}